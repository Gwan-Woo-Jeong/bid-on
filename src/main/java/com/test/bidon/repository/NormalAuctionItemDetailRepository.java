package com.test.bidon.repository;

import static com.test.bidon.entity.QNormalAuctionItem.normalAuctionItem;  // QNormalAuctionItem 임포트
import static com.test.bidon.entity.QNormalBidInfo.normalBidInfo;
import static com.test.bidon.entity.QUserEntity.userEntity;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
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
	                    normalAuctionItem.description, // auctionItemDescription
	                    userEntity.name,
	                    userEntity.email,
	                    userEntity.national // national
    	            )
    	            .from(normalBidInfo)
    	            .join(normalAuctionItem).on(normalBidInfo.auctionItemId.eq(normalAuctionItem.id))
    	            .join(userEntity).on(normalBidInfo.userInfoId.eq(userEntity.id))
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
                        .build())
    	            .collect(Collectors.toList());
    	    }
    	}