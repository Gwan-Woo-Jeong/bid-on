package com.test.bidon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class TestAdminController {

//	@GetMapping("/admin")
//	public String redirect(Model model) {
//
//		return "redirect:/admin/index";
//	}

	@GetMapping("/admin")
	public String index(Model model) {
		
		return "admin/index";
	}
	
	@GetMapping("/admin/auction")
	public String auction(Model model) {
		
		return "admin/auction";
	}
	
	@GetMapping("/admin/user")
	public String user(Model model) {
		
		return "admin/user";
	}
	
//	@GetMapping("/admin/community")
//	public String community(Model model) {
//		
//		return "admin/community";
//	}
	
	@GetMapping("/admin/login")
	public String login(Model model) {
		
		return "admin/login";
	}
	
}

