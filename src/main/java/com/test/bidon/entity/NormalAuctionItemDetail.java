//package com.test.bidon.entity;
//
//import java.time.LocalDateTime;
//
//import com.test.bidon.dto.NormalAuctionItemDetailDTO;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//@Entity
//@Getter
//@ToString
//@Table(name = "NormaAutcionItem")
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class NormalAuctionItemDetail {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	
//	private Long categorySubId;
//	
//	@Column(name = "userInfoId", nullable = false)
//	private String userInfoId;
//	
//	@Column(name = "name", nullable=false, length = 200)
//	private String name;
//	
//	@Column(name = "description", nullable = false, length = 500)
//	private String description;
//	
//	@Column(name = "startTime", nullable = false)
//	private LocalDateTime startTime;
//	
//	@Column(name = "endTime", nullable = false)
//	private LocalDateTime endTime;
//	
//	@Column(name = "startPrice", nullable = false)
//	private String startPrice;
//	
//	@Column(name = "status", nullable = false)
//	private String status;
//	
//	   public NormalAuctionItemDetailDTO toDTO() {
//	        return NormalAuctionItemDetailDTO.builder()
//	                .id(this.id)
//	                .categorySubId(this.categorySubId)
//	                .userInfoId(this.userInfoId)
//	                .name(this.name)
//	                .description(this.description)
//	                .startTime(this.startTime)
//	                .endTime(this.endTime)
//	                .startPrice(this.startPrice)
//	                .status(this.status)
//	                .build();
//	    }
//	}
