package com.test.bidon.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
		
		model.addAttribute("detail", liveAuctionDetail);
		model.addAttribute("images", detailImages);
		
		 System.out.println("images: " + detailImages); // 데이터 확인
		 
		 System.out.println("Images size: " + detailImages.size());

		 
		 
		    return "user/bid-detail-live";
	}
	
	
	

	
	
//	@GetMapping("/{id}")
//	@ResponseBody // JSON 형식으로 반환
//	public LiveAuctionDetailDTO getBidDetailLiveAsJson(@PathVariable("id") Long id) {
//	    return liveAuctionItemRepository.getAuctionDetail(id);
//	}
//	
}
