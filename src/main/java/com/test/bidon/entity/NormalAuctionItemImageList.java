package com.test.bidon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "NormalAuctionItemImageList")
@NoArgsConstructor
@AllArgsConstructor
public class NormalAuctionItemImageList {	//관계테이블 엔티티
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "normalAuctionItemImageId")
	private NormalAuctionItemImage normalAuctionItemImage;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "normalAuctionItemId")
	private NormalAuctionItem normalAuctionItem;
	
	private boolean isMainImage;
	
	//기본 생성자
    public NormalAuctionItemImageList(NormalAuctionItemImage normalAuctionItemImage, NormalAuctionItem normalAuctionItem, boolean isMainImage) {
        this.normalAuctionItemImage = normalAuctionItemImage;
        this.normalAuctionItem = normalAuctionItem;
        this.isMainImage = isMainImage;
    }
	
}
