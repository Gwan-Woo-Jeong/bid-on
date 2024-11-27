package com.test.bidon.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Table(name="NormalAuctionItemImage")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NormalAuctionItemImage {	//사진테이블 엔티티
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String path;
	
    @ManyToOne
	private List<NormalAuctionItemImageList> imageLists;
    
    public NormalAuctionItemImage(String path) {
    	this.path = path;
    }
}
