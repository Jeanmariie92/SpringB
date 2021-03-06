package com.jeanReb.app1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.jeanReb.app1.entity.User;
import com.jeanReb.app1.repository.RoleRepository;
import com.jeanReb.app1.repository.UserRepository;
import com.jeanReb.app1.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	// No usamos el Servicio debido a que con esta entidad solo realizaremos
	// consultas, asi evitamos tener una capa en el medio del proceso
	@Autowired
	RoleRepository roleRepository;

	@GetMapping("/")
	public String index() {

		return "index";
	}

	@GetMapping("/userForm")
	public String userForm(Model model) {

		model.addAttribute("userForm", new User());
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("listTab", "active");
		return "user-form/user-view";

	}


	@PostMapping("/userForm")
	public String createUser(@Valid @ModelAttribute("userForm") User user, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {

			model.addAttribute("userForm", user);
			model.addAttribute("formTab", "active");
			
		}else {
			
			try {
				userService.createUser(user);
				model.addAttribute("userForm", new User()); //Blanqueamos los cmapos del form
				model.addAttribute("listTab","active"); //Redirigimos a la pestaña List
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab", "active");
				
			}
		}
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles", roleRepository.findAll());
		return "user-form/user-view";

	}
	
	@GetMapping("/editUser/{id}")
	
	public String getEditUserForm(Model model, @PathVariable (name = "id")Long id) throws Exception{
		
		User userToEdit = userService.getUserById(id);
		
		model.addAttribute("userForm", userToEdit);
		
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("formTab", "active");
		model.addAttribute("editMode", "true");
		return "user-form/user-view";
		
	}
	

@PostMapping("/editUser")
	
	public String postEditUserForm(@Valid @ModelAttribute("userForm") User user, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {

			model.addAttribute("userForm", user);
			model.addAttribute("formTab", "active");
			model.addAttribute("editMode", "true");
			
		}else {
			
			try {
				userService.updateUser(user);
				model.addAttribute("userForm", new User()); //Blanqueamos los cmapos del form
				model.addAttribute("listTab","active"); //Redirigimos a la pestaña List
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab", "active");
				model.addAttribute("editMode", "true");
				
			}
		}
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles", roleRepository.findAll());
		return "user-form/user-view";

	}
		
@GetMapping("/userForm/cancel")
		
public String cancelEditUser(ModelMap model) {
	
	return "redirect:/userForm";
}
	}


