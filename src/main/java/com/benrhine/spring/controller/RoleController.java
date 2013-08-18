package com.benrhine.spring.controller;

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

import com.benrhine.spring.domain.Role;
import com.benrhine.spring.domain.User;
import com.benrhine.spring.service.RoleService;

@Controller
@RequestMapping(value="/role")
public class RoleController {
	private static final Logger logger = Logger.getLogger(RoleController.class);
	
	@Autowired private RoleService roleService;
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String addRolePage(ModelMap model) {
		model.addAttribute("role", new Role());
		return "role/add-role";
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addingRole(@ModelAttribute("role") @Valid final Role role, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("role", role);
			model.addAttribute("errorMessage", "error.have.occured");
			return "role/add-role";
		}
		
		try {
			Boolean success = roleService.addRole(role);
			
			if (success) {
				model.addAttribute("message", "role.added.success");
			} else {
				model.addAttribute("errorMessage", "role.already.exists");
			}
		} catch (Exception e) {
			logger.error(e);
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "admin/admin";
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String editRolePage(final Long id, ModelMap model) {
		try {
			final Role role = roleService.findById(id);
			model.addAttribute("role", role);
			model.addAttribute("user", role.getUser());
		} catch (HibernateException he) {
			logger.error(he);
			model.addAttribute("errorMessage", he.getMessage());
		} catch (RuntimeException e) {
			logger.error(e);
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "role/edit-role";
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public String editingRole(@ModelAttribute("role") @Valid final Role role, BindingResult result,
							  @ModelAttribute("user") @Valid final User user, BindingResult userResult, final Long id, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("role", role);
			model.addAttribute("errorMessage", "error.have.occured");
			return "role/edit-role";
		}
		
		try {
			role.setUser(user);
			Boolean success = roleService.updateRole(role);
			
			if (success) {
				model.addAttribute("message", "role.update.success");
			} else {
				model.addAttribute("errorMessage", "role.does.not.exists");
			}
		} catch (Exception e) {
			logger.error(e);
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "admin/admin";
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String deleteRolePage(final Long id, ModelMap model) {
		try {
			Boolean success = roleService.deleteRole(roleService.findById(id));
			
			if (success) {
				model.addAttribute("message", "role.delete.success");
			} else {
				model.addAttribute("errorMessage", "role.delete.error");
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
		return "redirect:/role/list";
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String listOfRoles(ModelMap model) {
		try {
			model.addAttribute("roles", roleService.findAllRoles());
		} catch (HibernateException he) {
			logger.error(he);
			model.addAttribute("errorMessage", he.getMessage());
		} catch (RuntimeException e) {
			logger.error(e);
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "role/list-roles";
	}
}
