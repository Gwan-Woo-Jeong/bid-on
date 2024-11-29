package com.test.bidon.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.bidon.dto.NormalBidInfoDTO;
import com.test.bidon.repository.CustomNormalAuctionItemRepository;
import com.test.bidon.repository.NormalAuctionItemDetailRepository;
import com.test.bidon.repository.NormalAuctionItemRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NormalAuctionController {

    private final NormalAuctionItemRepository normalAuctionItemRepository;
    private final CustomNormalAuctionItemRepository customNormalAuctionItemRepository;
    private final NormalAuctionItemDetailRepository normalAuctionItemDetailRepository;

    
	@GetMapping("/browse-bid")
	public String browseBid(Model model) {
		return "user/browse-bid";
	}
	
	@GetMapping("/bid-detail")
	public String bidDetail( Model model) {
		List<NormalBidInfoDTO> bidinfoList = normalAuctionItemDetailRepository.ItemDetail();
		
		model.addAttribute("bidinfoList", bidinfoList);
		
		return "user/bid-detail";
	}
}
