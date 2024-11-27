package com.test.bidon.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LiveAuctionDetailDTO {
	
	private Long auctionItemId;         // liveAuctionItem.id
    private Long userId;                // userEntity.id
    private String name;                // liveAuctionItem.name
    private String description;         // liveAuctionItem.description
    private Integer startPrice;          // liveAuctionItem.startPrice
    private LocalDateTime startTime;    // liveAuctionItem.startTime
    private String path;           // liveAuctionItemImage.path
    private Long liveCost;              // liveBidCost.id
    private Long liveAuctionPartId;     // liveBidCost.liveAuctionPartId
    private Long liveAuctionItemId;     // liveBidCost.liveAuctionItemId
    private Integer bidPrice;           // liveBidCost.bidPrice
    private String email;               // userEntity.email
    private String national;            // userEntity.national
    private String tel;                 // userEntity.tel
    private LocalDate createDate;   // userEntity.createDate
    
	    
	

}
