package com.test.bidon.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LiveAuctionItemListDTO {

	private Long id;
	private String name;
	private String path;
	private Integer startPrice;
	private LocalDateTime startTime;
	
	
	
	
}
