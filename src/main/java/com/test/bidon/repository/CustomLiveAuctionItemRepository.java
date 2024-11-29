package com.test.bidon.repository;

import static com.test.bidon.entity.QLiveAuctionItem.liveAuctionItem;
import static com.test.bidon.entity.QLiveAuctionItemImage.liveAuctionItemImage;
import static com.test.bidon.entity.QLiveAuctionItemImageList.liveAuctionItemImageList;
import static com.test.bidon.entity.QLiveAuctionPart.liveAuctionPart;
import static com.test.bidon.entity.QLiveBidCost.liveBidCost;
import static com.test.bidon.entity.QUserEntity.userEntity;

import java.util.List;

import com.test.bidon.dto.*;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

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


	public LiveAuctionItemListDTO bigHomeLiveAuctionList() {

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
			        .orderBy(liveAuctionItem.id.desc()) // ID 기준 내림차순 정렬
			        .limit(1) // 상위 1개만 가져옴
			        .fetchOne();

	}




	public List<LiveAuctionItemListDTO> HomeLiveAuctionList(){


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
			        .orderBy(liveAuctionItem.id.asc()) // id 오름차순 정렬
			        .offset(2) // 2번째 레코드부터 시작
			        .limit(4) // 최대 4개 가져오기
			        .fetch();
}




	public LiveAuctionDetailDTO getAuctionDetail(Long liveAuctionItemId) {

		return jpaQueryFactory
				.select(Projections.fields(
						LiveAuctionDetailDTO.class,
						liveAuctionItem.name.as("productName"),
						liveAuctionItem.startPrice,
						liveAuctionItem.startTime,
						liveAuctionItem.description,
						liveAuctionItem.brand,
						liveAuctionItem.createTime,


						liveAuctionPart.id.count().as("participantCount"),

						userEntity.name.as("sellerName"),
						userEntity.email.as("sellerEmail"),
						userEntity.createDate.as("sellerJoinDate"),
						userEntity.national.as("sellerNational"),
						userEntity.tel.as("sellerTel"),

						liveBidCost.bidPrice.max().as("lastBidPrice")
						))
				.from(liveAuctionItem)
		        .leftJoin(liveAuctionPart).on(liveAuctionPart.liveAuctionItemId.eq(liveAuctionItem.id))
		        .leftJoin(userEntity).on(userEntity.id.eq(liveAuctionItem.userInfoId))
		        .leftJoin(liveBidCost).on(liveBidCost.liveAuctionItemId.eq(liveAuctionItem.id))
		        .where(liveAuctionItem.id.eq(liveAuctionItemId))
		        .groupBy(
		            liveAuctionItem.name,
		            liveAuctionItem.startPrice,
		            liveAuctionItem.startTime,
		            liveAuctionItem.description,
		            liveAuctionItem.brand,
					liveAuctionItem.createTime,
		            userEntity.name,
		            userEntity.email,
		            userEntity.createDate,
		            userEntity.national,
		            userEntity.tel
		        )
		        .fetchOne();
	}


	public List<LiveAuctionDetailImagesDTO> detailImages(Long liveAuctionItemId){


		return jpaQueryFactory
				.select(Projections.fields(LiveAuctionDetailImagesDTO.class,
						liveAuctionItem.id.as("itemId"),
						liveAuctionItemImage.path.as("imagePath"),
						liveAuctionItemImageList.isMainImage
						))
				 .from(liveAuctionItemImageList)
			     .join(liveAuctionItemImageList.liveAuctionItemImage, liveAuctionItemImage)
				.where(liveAuctionItemImageList.liveAuctionItem.id.eq(liveAuctionItemId))
				.orderBy(liveAuctionItemImageList.isMainImage.desc())
				.fetch();
	}


	public List<LiveAuctionDetailCustomerDTO> bidCustomer(Long liveAuctionItemId){

		return jpaQueryFactory
				.select(Projections.fields(LiveAuctionDetailCustomerDTO.class,
						liveBidCost.id,
						liveBidCost.bidPrice,
						liveBidCost.bidTime,
						userEntity.name.as("customerName"),
						userEntity.national.as("customerNational")
						))
				.from(liveBidCost)
				.join(liveAuctionPart).on(liveBidCost.liveAuctionPartId.eq(liveAuctionPart.id))
				.join(userEntity).on(liveAuctionPart.userInfoId.eq(userEntity.id))
				.where(liveBidCost.liveAuctionItemId.eq(liveAuctionItemId))
				.orderBy(liveBidCost.bidTime.desc())
				.fetch();

	}
	
	
	
	

}