package com.test.bidon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveAuctionItemListDTO {

	private Long id;
	private String name;
	private String path;
	private Integer startPrice;
	
}
