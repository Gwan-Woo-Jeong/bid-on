package com.test.bidon.controller;

import java.util.List;

import com.querydsl.core.Tuple;
import com.test.bidon.repository.CustomNormalAuctionItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.bidon.dto.LiveAuctionItemListDTO;
import com.test.bidon.repository.CustomLiveAuctionItemRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController {
	
	private final CustomLiveAuctionItemRepository liveAuctionItemRepository;
	private final CustomNormalAuctionItemRepository customNormalAuctionItemRepository;
	

	@GetMapping("/")
	public String redirect(Model model) {
		return "redirect:/home";
	}

	@GetMapping("/home")
	public String index(Model model) {
		
		List<LiveAuctionItemListDTO> HomeLiveItemList = liveAuctionItemRepository.LiveAuctionList(0, 6, null, null, null);
		List<Tuple> HomeNormalItemList = customNormalAuctionItemRepository.DisplaySixItems();
		List<Tuple> HomeNormalTimeAndPriceList = customNormalAuctionItemRepository.DisplaySixTimeAndPrice();

		HomeLiveItemList.sort((a,b) -> b.getId().compareTo(a.getId()));
		
		List<LiveAuctionItemListDTO> limitedItems = HomeLiveItemList.stream().skip(2).limit(4).toList();

		LiveAuctionItemListDTO bigList = liveAuctionItemRepository.bigHomeLiveAuctionList();

		model.addAttribute("liveItemList", limitedItems);
		model.addAttribute("big", bigList);
		model.addAttribute("normalItemList", HomeNormalItemList);
		model.addAttribute("normalTimeAndPriceList", HomeNormalTimeAndPriceList);
		
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

//	private final NormalAuctionItemDetailRepository normalAuctionItemDetailRepository;
//	@GetMapping("/bid-detail")
//	public String bidDetail(Model model) {
//		List<NormalBidInfoDTO> bidinfoList = normalAuctionItemDetailRepository.ItemDetail();
//		
//		System.out.println(bidinfoList.getLast().getNational());
//		model.addAttribute("bidinfoList", bidinfoList);
//		
//		return "user/bid-detail";
//	}
	
//	@GetMapping("/bid-detail-live")
//	public String bidDetailLive(Model model) {
//		return "user/bid-detail-live";
//	}

	@GetMapping("/bid-history")
	public String bidHistory(Model model) {
		return "user/bid-history";
	}

	
//	@GetMapping("/blog") 
//	public String blog(Model model) 
//	{ return "user/blog"; }

//	@GetMapping("/blog-detail")
//	public String blogDetail(Model model) {
//		return "user/blog-detail";
//	}

//	@GetMapping("/browse-bid")
//	public String browseBid(Model model) {
//		return "user/browse-bid";
//	}
	
//	@GetMapping("/browse-live-bid")
//	public String browseLiveBid(Model model) {
//		return "user/browse-live-bid";
//	}

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

	// @GetMapping("/bid-live")
	// public String bidLive(Model model) {
	// 	return "user/bid-live";
	// }
	
	@GetMapping("/bid-registration")
	public String bidRegistration(Model model) {
		return "user/bid-registration";
	}

	@GetMapping("/bid-success")
	public String bidSuccess(Model model) {
		return "user/bid-success";
	}

}
