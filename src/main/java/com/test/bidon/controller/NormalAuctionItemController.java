//package com.test.bidon.controller;
//
//import java.util.List;
//
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.test.bidon.entity.NormalBidInfo;
//import com.test.bidon.repository.NormalAuctionItemDetailRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequiredArgsConstructor
//public class NormalAuctionItemController {
//	
//	private final NormalAuctionItemDetailRepository normalAuctionItemDetailRepository;
//	
//	@GetMapping("/bid-detail")
//	public String blogDetail(Model model) {
//		
//		List<NormalBidInfo> list = normalAuctionItemDetailRepository.ItemDetail();
//		
//		model.addAttribute("list", list);
//		
//		return "user/bid-detail";
//	}
//	
//
//
//}
