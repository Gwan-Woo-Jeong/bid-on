package com.test.bidon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

	@GetMapping("/")
	public String redirect(Model model) {
		return "redirect:/home";
	}

	@GetMapping("/home")
	public String index(Model model) {
		return "user/home";
	}

	@GetMapping("/404")
	public String notFound(Model model) {
		return "user/404";
	}

	@GetMapping("/about")
	public String about(Model model) {
		return "user/about";
	}

	@GetMapping("/bid-detail")
	public String bidDetail(Model model) {
		return "user/bid-detail";
	}
	
	@GetMapping("/bid-detail-live")
	public String bidDetailLive(Model model) {
		return "user/bid-detail-live";
	}

	@GetMapping("/bid-history")
	public String bidHistory(Model model) {
		return "user/bid-history";
	}

	
	@GetMapping("/blog") 
	public String blog(Model model) 
	{ return "user/blog"; }

	@GetMapping("/blog-detail")
	public String blogDetail(Model model) {
		return "user/blog-detail";
	}

	@GetMapping("/browse-bid")
	public String browseBid(Model model) {
		return "user/browse-bid";
	}
	
	@GetMapping("/browse-live-bid")
	public String browseLiveBid(Model model) {
		return "user/browse-live-bid";
	}

	@GetMapping("/checkout")
	public String checkout(Model model) {
		return "user/checkout";
	}

	@GetMapping("/contact")
	public String contact(Model model) {
		return "user/contact";
	}

	@GetMapping("/cookies-policy")
	public String cookiesPolicy(Model model) {
		return "user/cookies-policy";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		return "user/dashboard";
	}

	@GetMapping("/faq")
	public String faq(Model model) {
		
		return "user/faq";
	}

//	@GetMapping("/login")
//	public String login(Model model) {
//		return "user/login";
//	}

	@GetMapping("/payment")
	public String payment(Model model) {
		return "user/payment";
	}

	@GetMapping("/privacy")
	public String privacy(Model model) {
		return "user/privacy";
	}

//	@GetMapping("/signup")
//	public String signup(Model model) {
//		return "user/signup";
//	}

	@GetMapping("/term-condition")
	public String termCondition(Model model) {
		return "user/term-condition";
	}

	@GetMapping("/thankyou")
	public String thankyou(Model model) {
		return "user/thankyou";
	}

	@GetMapping("/winner")
	public String winner(Model model) {
		return "user/winner";
	}

	@GetMapping("/bid-live")
	public String bidLive(Model model) {
		return "user/bid-live";
	}

}
