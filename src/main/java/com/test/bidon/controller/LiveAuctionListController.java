package com.test.bidon.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.bidon.dto.LiveAuctionItemListDTO;
import com.test.bidon.entity.LiveAuctionItemImageList;
import com.test.bidon.repository.CustomLiveAuctionItemRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LiveAuctionListController {
	
	private final CustomLiveAuctionItemRepository liveAuctionItemRepository;
	
	
	@GetMapping("/browse-live-bid")
	public String browseLiveBid(Model model) {
		
		List<LiveAuctionItemListDTO> liveItemList = liveAuctionItemRepository.LiveAuctionList();
		
		model.addAttribute("liveItemList", liveItemList);
		
		return "user/browse-live-bid";
	}
	

}
