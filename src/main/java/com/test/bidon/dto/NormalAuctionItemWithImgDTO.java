package com.test.bidon.dto;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NormalAuctionItemWithImgDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NormalAuctionItemWithImgDTO_seq_generator")
	@SequenceGenerator(name = "NormalAuctionItemWithImgDTO_seq_generator", sequenceName = "id", allocationSize = 1)
	private Long id;
	
	private String name;
	private List<String> imagePath;
	
//	public NormalAuctionItemWithImgDTO(Long id, String name, List<String> imagePath) {
//		this.id = id;
//		this.name = name;
//		this.imagePath = imagePath;
//	}
	

}
