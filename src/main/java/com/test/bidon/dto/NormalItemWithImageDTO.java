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
public class NormalItemWithImageDTO {

	private Long id;
	private Long normalAuctionItemImageId;
	private Long normalAuctionItemId;
	private String path;
	private Integer isMainImage; 
	
	@QueryProjection
	public NormalItemWithImageDTO(Long id, Long normalAuctionItemImageId, Long normalAuctionItemId, String path, Integer isMainImage) {
		this.id = id;
		this.normalAuctionItemImageId = normalAuctionItemImageId;
		this.normalAuctionItemId = normalAuctionItemId;
		this.path = path;
		this.isMainImage = isMainImage;
		
	}

	
}
