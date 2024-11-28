package com.test.bidon.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LiveAuctionDetailCustomerDTO {

	
		
		public class BidDetailDTO {
	    private Long bidId;             // 입찰 ID
	    private Long customerName;      // 입찰자 ID
	    private String bidderNational;  // 입찰자 국적
	    private Integer bidPrice;       // 입찰 가격
	    private LocalDateTime bidTime;  // 입찰 시간
	}
	
	
	
}
