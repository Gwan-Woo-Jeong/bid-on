package com.test.bidon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

	@GetMapping("/")
	public String redirect(Model model) {

		return "redirect:/index";
	}

	@GetMapping("/index")
	public String index(Model model) {

		return "index";
	}

	@GetMapping("/404")
	public String notFound(Model model) {

		return "404";
	}

	@GetMapping("/about")
	public String about(Model model) {

		return "about";
	}

	@GetMapping("/bid-detail")
	public String bidDetail(Model model) {

		return "bid-detail";
	}

	@GetMapping("/bid-history")
	public String bidHistory(Model model) {

		return "bid-history";
	}

	@GetMapping("/blog")
	public String blog(Model model) {

		return "blog";
	}

	@GetMapping("/blog-detail")
	public String blogDetail(Model model) {

		return "blog-detail";
	}

	@GetMapping("/browse-bid")
	public String browseBid(Model model) {

		return "browse-bid";
	}

	@GetMapping("/checkout")
	public String checkout(Model model) {

		return "checkout";
	}

	@GetMapping("/contact")
	public String contact(Model model) {

		return "contact";
	}

	@GetMapping("/cookies-policy")
	public String cookiesPolicy(Model model) {

		return "cookies-policy";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model) {

		return "dashboard";
	}

	@GetMapping("/faq")
	public String faq(Model model) {

		return "faq";
	}

	@GetMapping("/login")
	public String login(Model model) {

		return "login";
	}

	@GetMapping("/payment")
	public String payment(Model model) {

		return "payment";
	}

	@GetMapping("/privacy")
	public String privacy(Model model) {

		return "privacy";
	}

	@GetMapping("/signup")
	public String signup(Model model) {

		return "signup";
	}

	@GetMapping("/term-condition")
	public String termCondition(Model model) {

		return "term-condition";
	}

	@GetMapping("/thankyou")
	public String thankyou(Model model) {

		return "thankyou";
	}

	@GetMapping("/winner")
	public String winner(Model model) {

		return "winner";
	}

}

