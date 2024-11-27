package com.test.bidon.repository;

import static com.test.bidon.entity.QLiveAuctionItem.liveAuctionItem;
import static com.test.bidon.entity.QLiveAuctionItemImage.liveAuctionItemImage;
import static com.test.bidon.entity.QLiveAuctionItemImageList.liveAuctionItemImageList;
import static com.test.bidon.entity.QLiveAuctionPart.liveAuctionPart;
import static com.test.bidon.entity.QLiveBidCost.liveBidCost;
import static com.test.bidon.entity.QUserEntity.userEntity;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.bidon.dto.LiveAuctionDetailDTO;
import com.test.bidon.dto.LiveAuctionItemListDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomLiveAuctionItemRepository {
	
	private final JPAQueryFactory jpaQueryFactory;
	
	
	public List<LiveAuctionItemListDTO> LiveAuctionList(){
		

		return jpaQueryFactory
				.select(Projections.constructor(
			            LiveAuctionItemListDTO.class,
			            liveAuctionItem.id,
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
	
	public Tuple findDetailById(Long id){
		
		
		return jpaQueryFactory
				 .select(Projections.fields(
				         LiveAuctionDetailDTO.class,
						 liveAuctionItem.id.as("auctionItemId"),
				            userEntity.id.as("userId"),
				            liveAuctionItem.name,
				            liveAuctionItem.description,
				            liveAuctionItem.startPrice,
				            liveAuctionItem.startTime,
				            liveAuctionItemImage.path,
				            liveBidCost.id.as("liveCost"),
				            liveBidCost.liveAuctionPartId,
				            liveBidCost.liveAuctionItemId,
				            liveBidCost.bidPrice,
				            userEntity.email,
				            userEntity.national,
				            userEntity.tel,
				            userEntity.createDate
				        )
				        .from(liveAuctionItem)
				        .leftJoin(liveAuctionItemImage).on(liveAuctionItemImageList.liveAuctionItemId.eq(liveAuctionItem.id))
				        .leftJoin(liveBidCost).on(liveBidCost.liveAuctionItemId.eq(liveAuctionItem.id))
				        .leftJoin(userEntity).on(userEntity.id.eq(liveAuctionItem.userInfoId))
				        .where(liveAuctionItem.id.eq(id))
				        .fetchOne();
	}

	
	
	
	

}
