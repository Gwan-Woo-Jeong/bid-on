package com.test.bidon.repository;

import static com.test.bidon.entity.QLiveAuctionItem.liveAuctionItem;
import static com.test.bidon.entity.QLiveAuctionItemImage.liveAuctionItemImage;
import static com.test.bidon.entity.QLiveAuctionItemImageList.liveAuctionItemImageList;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.bidon.dto.LiveAuctionItemListDTO;
import com.test.bidon.entity.LiveAuctionItemImageList;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomLiveAuctionItemRepository {
	
	private final JPAQueryFactory jpaQueryFactory;
	
	
	public List<LiveAuctionItemListDTO> LiveAuctionList(){
		

		return jpaQueryFactory
				.select(Projections.constructor(
			            LiveAuctionItemListDTO.class,
			            liveAuctionItem.name,
			            liveAuctionItemImage.path,
			            liveAuctionItem.startPrice
			        ))
			        .from(liveAuctionItemImageList)
			        .join(liveAuctionItemImageList.liveAuctionItemImage, liveAuctionItemImage)
			        .join(liveAuctionItemImageList.liveAuctionItem, liveAuctionItem)
			        .where(liveAuctionItemImageList.isMainImage.eq(1))
			        .fetch();
			
					
					
		
	}

}
