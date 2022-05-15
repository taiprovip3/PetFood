package springboot.petfood.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import springboot.petfood.dao.UserDaoJpaImpl;
import springboot.petfood.entity.Role;
import springboot.petfood.entity.User;
import springboot.petfood.entity.UserRole;

@Controller
@RequestMapping("/common")
public class CommonController {
	
	@Autowired
	private UserDaoJpaImpl userDaoJpaImpl;
	
	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}
	
	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		User user = new User();
		model.addAttribute("USER_DATA", user);
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("USER_DATA") User user, Model model) {
		Role role = new Role();
		role.setRoleId(1);
		role.setNameRole("MEMBER");
		userDaoJpaImpl.saveUser(user,role);
		System.out.println("Register success user :: "+user);
		return "login";
	}
	
	@GetMapping("/403")
	public String showAccessDeniedPage() {
		return "access-denied";
	}
}
