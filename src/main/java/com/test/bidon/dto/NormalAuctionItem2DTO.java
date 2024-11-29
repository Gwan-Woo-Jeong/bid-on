package com.test.bidon.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
public class NormalAuctionItem2DTO {
	
	private Long auctionItemId; 
	private String auctionItemName;
	private String path;
	private Integer startPrice;
	
	@QueryProjection
	public NormalAuctionItem2DTO(Long auctionItemId, String auctionItemName, String path,Integer startPrice) {
		
		this.auctionItemId = auctionItemId;
		this.auctionItemName = auctionItemName;
		this.path = path;
		this.startPrice = startPrice;
	}

}
