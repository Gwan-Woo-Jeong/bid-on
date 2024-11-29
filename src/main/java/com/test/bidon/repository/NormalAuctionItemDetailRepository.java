package com.test.bidon.repository;

import static com.test.bidon.entity.QNormalAuctionItem.normalAuctionItem;  // QNormalAuctionItem 임포트
import static com.test.bidon.entity.QNormalAuctionItemImage.normalAuctionItemImage;
import static com.test.bidon.entity.QNormalAuctionItemImageList.normalAuctionItemImageList;
import static com.test.bidon.entity.QNormalBidInfo.normalBidInfo;
import static com.test.bidon.entity.QUserEntity.userEntity;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.bidon.dto.NormalAuctionItem2DTO;
import com.test.bidon.dto.NormalBidInfoDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NormalAuctionItemDetailRepository {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    public List<NormalBidInfoDTO> ItemDetail() {
    	  return jpaQueryFactory
    	            .select(
	                    normalBidInfo.id,
	                    normalBidInfo.auctionItemId,
	                    normalBidInfo.userInfoId,
	                    normalBidInfo.bidPrice,
	                    normalBidInfo.bidDate,
	                    normalAuctionItem.name, // auctionItemName
	                    normalAuctionItem.description, 
	                    userEntity.name,
	                    userEntity.email,
	                    userEntity.national,
	                    normalAuctionItem.id
    	            )
    	            .from(normalBidInfo)
    	            .join(normalAuctionItem).on(normalBidInfo.auctionItemId.eq(normalAuctionItem.id))
    	            .join(userEntity).on(normalBidInfo.userInfoId.eq(userEntity.id))
    	            
    	            .where(normalAuctionItem.id.eq(normalBidInfo.auctionItemId))
    	            .fetch()
    	            .stream()
    	            .map(record -> NormalBidInfoDTO.builder()
                        .id(record.get(normalBidInfo.id))
                        .auctionItemId(record.get(normalBidInfo.auctionItemId))
                        .userInfoId(record.get(normalBidInfo.userInfoId))
                        .bidPrice(record.get(normalBidInfo.bidPrice))
                        .bidDate(record.get(normalBidInfo.bidDate))
                        .auctionItemName(record.get(normalAuctionItem.name))  // 필드명 맞추기
                        .auctionItemDescription(record.get(normalAuctionItem.description))  // 필드명 맞추기
                        .bidderName(record.get(userEntity.name))
                        .bidderEmail(record.get(userEntity.email))  // 필드명 맞추기
                        .national(record.get(userEntity.national))
                        .id(record.get(normalAuctionItem.id))
                        .build())
    	            .collect(Collectors.toList());
    	    }
    
    

	public List<NormalAuctionItem2DTO> NormalAuctionList() {
		return jpaQueryFactory
				 .select(
						 normalAuctionItem.id,
						 normalAuctionItem.name,
						 normalAuctionItem.startPrice,
						 normalAuctionItemImage.path
					)
				 .from(normalAuctionItem)
				    .join(normalAuctionItemImageList)
				        .on(normalAuctionItemImageList.normalAuctionItem.id.eq(normalAuctionItem.id)) // 정상적으로 객체를 통해 연결
				    .join(normalAuctionItemImage)
				        .on(normalAuctionItemImage.id.eq(normalAuctionItemImageList.normalAuctionItemImage.id)) // 정상적으로 객체를 통해 연결
				        .where(normalAuctionItemImageList.isMainImage.eq(1)) // isMainImage가 true인 경우
				    .fetch()
				    .stream()
				    .map(record -> NormalAuctionItem2DTO.builder()
				        .auctionItemName(record.get(normalAuctionItem.name))
				        .path(record.get(normalAuctionItemImage.path)) // 필드명 맞추기
				        .startPrice(record.get(normalAuctionItem.startPrice)) // 필드명 맞추기
				        .auctionItemId(record.get(normalAuctionItem.id)) // 필드명 맞추기
				        .build())
				    .collect(Collectors.toList());
	}
    	}