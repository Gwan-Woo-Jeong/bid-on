package com.test.bidon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.querydsl.core.Tuple;
import com.test.bidon.dto.LiveAuctionDetailDTO;
import com.test.bidon.repository.CustomLiveAuctionItemRepository;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/bid-detail-live")
public class LiveAuctionDetailController {
	
	private final CustomLiveAuctionItemRepository liveAuctionItemRepository;

	
	
	@GetMapping("/{id}")
	public String bidDetailLive(@PathVariable("id") Long id, Model model) {
		
		 Tuple tuple = liveAuctionItemRepository.findDetailById(id);

		    LiveAuctionDetailDTO detail = new LiveAuctionDetailDTO(
		        tuple.get(liveAuctionItem.id),
		        tuple.get(userEntity.id),
		        tuple.get(liveAuctionItem.name),
		        tuple.get(liveAuctionItem.description),
		        tuple.get(liveAuctionItem.startPrice),
		        tuple.get(liveAuctionItem.startTime),
		        tuple.get(liveAuctionItemImage.path),
		        tuple.get(liveBidCost.id),
		        tuple.get(liveBidCost.liveAuctionPartId),
		        tuple.get(liveBidCost.liveAuctionItemId),
		        tuple.get(liveBidCost.bidPrice),
		        tuple.get(userEntity.email),
		        tuple.get(userEntity.national),
		        tuple.get(userEntity.tel),
		        tuple.get(userEntity.createDate)
		    );

		    model.addAttribute("detail", detail);
		    return "user/bid-detail-live";
	}
	
}
