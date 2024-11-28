package com.test.bidon.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.test.bidon.dto.LiveAuctionDetailCustomerDTO;
import com.test.bidon.dto.LiveAuctionDetailDTO;
import com.test.bidon.dto.LiveAuctionDetailImagesDTO;
import com.test.bidon.repository.CustomLiveAuctionItemRepository;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class LiveAuctionDetailController {
	
	private final CustomLiveAuctionItemRepository liveAuctionItemRepository;

	
	
	@GetMapping("/bid-detail-live/{id}")
	public String bidDetailLive(@PathVariable("id") Long id, Model model) {
		
		List<LiveAuctionDetailImagesDTO> detailImages = liveAuctionItemRepository.detailImages(id);
		
		LiveAuctionDetailDTO liveAuctionDetail = liveAuctionItemRepository.getAuctionDetail(id);
		
		List<LiveAuctionDetailCustomerDTO> customerBid = liveAuctionItemRepository.bidCustomer(id);
		
		model.addAttribute("detail", liveAuctionDetail);
		model.addAttribute("images", detailImages);
		model.addAttribute("cutomer", customerBid);
			 	 
		    return "user/bid-detail-live";
	}
	
	


}
