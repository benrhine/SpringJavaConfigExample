package com.benrhine.spring.controller;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
@RequestMapping(value="/user")
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired private UserService userService;

	@Secured({"ROLE_USER"})
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String addUserPage(ModelMap model) {
		model.addAttribute("userDto", new UserDto());
		return "user/add-user";
	}
	
	@Secured({"ROLE_USER"})
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addingUser(@ModelAttribute("userDto") @Valid final UserDto userDto, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("userDto", userDto);
			model.addAttribute("errorMessage", "error.have.occured");
			return "user/add-user";
		}
		try {
			User user = UserMapper.mapClone(userDto);
			user.setRole(new Role(RoleUtil.ROLE_USER, user));
			
			Boolean success = userService.addUser(user);
			
			if (success) {
				model.addAttribute("message", "user.added.success");
			} else {
				model.addAttribute("errorMessage", "user.already.exists");
			}
		} catch (ConstraintViolationException cve) {
			logger.error(cve);
			model.addAttribute("errorMessage", cve.getMessage());
		} catch (Exception e) {
			logger.error(e);
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "index";
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String editUserPage(final Long id, ModelMap model) {
		try {
			model.addAttribute("userDto", UserMapper.map(userService.findById(id)));
		} catch (HibernateException he) {
			logger.error(he);
			model.addAttribute("errorMessage", he.getMessage());
		} catch (RuntimeException e) {
			logger.error(e);
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "user/edit-user";
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public String editingUser(@ModelAttribute("userDto") @Valid final UserDto userDto, BindingResult result, final Long id, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("userDto", userDto);
			model.addAttribute("errorMessage", "error.have.occured");
			return "user/edit-user";
		}
		try {
			User user = UserMapper.mapClone(userDto);
			user.setRole(new Role(RoleUtil.ROLE_USER, user));
			
			Boolean success = userService.updateUser(user);
			
			if (success) {
				model.addAttribute("message", "user.update.success");
			} else {
				model.addAttribute("errorMessage", "user.does.not.exists");
			}
		} catch (ConstraintViolationException cve) {
			logger.error(cve);
			model.addAttribute("errorMessage", cve.getMessage());
		} catch (Exception e) {
			logger.error(e);
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "admin/admin";
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String deleteUserPage(final Long id, ModelMap model) {
		try {
			Boolean success = userService.deleteUser(userService.findById(id));
			
			if (success) {
				model.addAttribute("message", "user.delete.success");
			} else {
				model.addAttribute("errorMessage", "user.delete.error");
			}
		} catch (HibernateException he) {
			logger.error(he);
			model.addAttribute("errorMessage", he.getMessage());
		} catch (RuntimeException re) {
			logger.error(re);
			model.addAttribute("errorMessage", re.getMessage());
		} catch (Exception e) {
			logger.error(e);
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/user/list";
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String listOfUsers(ModelMap model) {
		try {
			model.addAttribute("users", userService.findAllUsers());
		} catch (HibernateException he) {
			logger.error(he);
			model.addAttribute("errorMessage", he.getMessage());
		} catch (RuntimeException e) {
			logger.error(e);
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "user/list-users";
	}
}
