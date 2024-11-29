package com.test.bidon.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LiveAuctionDetailCustomerDTO {


	    private Long bidId;             // 입찰 ID
	    private Integer bidPrice;       // 입찰 가격
	    private LocalDateTime bidTime;  // 입찰 시간
	    private String customerName;      // 입찰자 이름
	    private String customerNational;  // 입찰자 국적


	
}
