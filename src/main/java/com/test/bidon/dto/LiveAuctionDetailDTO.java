package com.test.bidon.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveAuctionDetailDTO {
	
	  	private String productName;            // 상품명
	    private Integer startPrice;     // 시작 경매가
	    private LocalDateTime startTime; // 경매 시작 시간
	    private String description; //상품설명
	    private String brand;
	    private LocalDateTime createTime;
	    private Long participantCount;  // 경매 참여자 수


	    // 판매자 정보
	    private String sellerName;
	    private String sellerEmail;
	    private LocalDate sellerJoinDate;
	    private String sellerNational;
	    private String sellerTel;
	    
	    //최신경매가
	    private Integer lastBidPrice;

	    // 입찰 정보
	    private List<LiveAuctionDetailCustomerDTO> bids;
    
	    
	

}
