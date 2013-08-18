package com.benrhine.spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.benrhine.spring.data.mapper.UserMapper;
import com.benrhine.spring.data.transfer.UserDto;
import com.benrhine.spring.domain.Role;
import com.benrhine.spring.domain.User;
import com.benrhine.spring.service.UserService;
import com.benrhine.spring.util.RoleUtil;

@Controller
@RequestMapping(value="/auth")
public class AccessController {
	private static final Logger logger = Logger.getLogger(AccessController.class);
	
	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private UserService userService;
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(ModelMap model, HttpServletRequest request) {
		
		final String error = this.isloginError(request);
		
		if (error != null) {
			model.addAttribute("errorMessage", error);
		}
		return "access/login";
	}
	
	@RequestMapping(value="/login/failure", method = RequestMethod.GET)
	public String loginError(ModelMap model) {
		return "access/login";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		return "access/login";
	}
	
	@RequestMapping(value="/logout/success", method = RequestMethod.GET)
	public String logoutSuccess(ModelMap model) {
		return "access/login";
	}
	
	@RequestMapping(value="/logout/failure", method = RequestMethod.GET)
	public String logoutError(ModelMap model) {
		return null;
	}
	
	@RequestMapping(value="/denied", method = RequestMethod.GET)
	public String denied(ModelMap model) {
		model.addAttribute("errorMessage", "access.denied.message");		
		return "access/denied";
	}
	
	@RequestMapping(value="/signup", method = RequestMethod.GET)
	public String signup(ModelMap model) {
		model.addAttribute("userDto", new UserDto());
		return "access/signup";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String createAccount(@ModelAttribute("userDto") @Valid UserDto userDto, BindingResult result, HttpServletRequest request, ModelMap model) {
		try {
			if (result.hasErrors()) {
				model.addAttribute("userDto", userDto);
				model.addAttribute("errorMessage", "error.have.occured");
				return "access/signup";
			}
						
//			if (userService.findByUsername(userDto.getUserName()) != null) {
//				model.addAttribute("errorMessage", "signup.invalid.username.duplicate");
//				return "access/signup";
//			}
			
			if (userDto.getUserName().equals(userDto.getReUserName()) == false) {
				model.addAttribute("errorMessage", "signup.invalid.username.notmatching");
				return "access/signup";
			}
			
			if (userDto.getUserPass().equals(userDto.getReUserPass()) == false) {
				model.addAttribute("errorMessage", "signup.invalid.password.notmatching");
				return "access/signup";
			}
			User user = UserMapper.map(userDto);
			user.setRole(new Role(RoleUtil.ROLE_USER, user)); // how to handle this better
			
			model.addAttribute("user", user);
			
			boolean success = userService.addUser(user);
			
			if (success) {
				model.addAttribute("status", "User was successfully added.");
				model.addAttribute("message", "Thank you for your registration.");
				this.authenticateUserAndSetSession(user, request);
				return "index";
			} else {
				model.addAttribute("errorMessage", "signup.invalid.username.duplicate");
				return "access/signup";
			}			
		} catch (Exception e) {
			logger.error(e);
			
			model.addAttribute("errorMessage", "could.not.create.new.user");
			return "access/signup";
		}
	}
	
	/**
	 * Private helper methods.
	 * 
	 * @param request
	 * @return
	 */
	private String isloginError(HttpServletRequest request) {
		Exception e = (Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		if (e != null) {
			if (e.getMessage().equalsIgnoreCase("java.lang.NullPointerException")) {
				return "login.failure.username.notfound";
			} else if (e.getMessage().equalsIgnoreCase("Bad Credentials")) {
				return "login.failure.username.password.incorrect";
			} else {
				return e.getMessage();
			}
		}
		return null;
	}
	
	private void authenticateUserAndSetSession(@ModelAttribute("user") @Valid final User user, HttpServletRequest request) {
		/*if (logger.isDebugEnabled()) {
			LogUtil.logUserObject(logger, user);
		}*/
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		token.setDetails(user);
		
		Authentication authenticatedUser = authenticationManager.authenticate(token);
		
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		
		request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
	}
}
 