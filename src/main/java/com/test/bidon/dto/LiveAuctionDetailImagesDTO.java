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
public class LiveAuctionDetailImagesDTO {
	
	private Long id;
	private String imagePath;
	private Integer isMainImage;
	
}
