package com.test.bidon.repository;

import static com.test.bidon.entity.QNormalAuctionItem.normalAuctionItem;  // QNormalAuctionItem 임포트
import static com.test.bidon.entity.QNormalAuctionItemImage.normalAuctionItemImage;
import static com.test.bidon.entity.QNormalAuctionItemImageList.normalAuctionItemImageList;
import static com.test.bidon.entity.QNormalBidInfo.normalBidInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.bidon.dto.NormalBidInfoDTO;
import com.test.bidon.entity.QUserEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NormalAuctionItemDetailRepository {

	private final JPAQueryFactory jpaQueryFactory;

//	public List<NormalBidInfoDTO> ItemDetail() {
//		return jpaQueryFactory
//				.select(
//						normalBidInfo.id,
//						normalBidInfo.auctionItemId,
//						normalBidInfo.userInfoId,
//						normalBidInfo.bidPrice,
//						normalBidInfo.bidDate,
//						normalAuctionItem.name, // auctionItemName
//						normalAuctionItem.description,
//						userEntity.name,
//						userEntity.email,
//						userEntity.national,
//						normalAuctionItem.id
//				)
//				.from(normalBidInfo)
//				.join(normalAuctionItem).on(normalBidInfo.auctionItemId.eq(normalAuctionItem.id))
//				.join(userEntity).on(normalBidInfo.userInfoId.eq(userEntity.id))
//
//				.fetch()
//				.stream()
//				.map(record -> NormalBidInfoDTO.builder()
//						.id(record.get(normalBidInfo.id))
//						.auctionItemId(record.get(normalBidInfo.auctionItemId))
//						.userInfoId(record.get(normalBidInfo.userInfoId))
//						.bidPrice(record.get(normalBidInfo.bidPrice))
//						.bidDate(record.get(normalBidInfo.bidDate))
//						.auctionItemName(record.get(normalAuctionItem.name))  // 필드명 맞추기
//						.auctionItemDescription(record.get(normalAuctionItem.description))  // 필드명 맞추기
//						.bidderName(record.get(userEntity.name))
//						.bidderEmail(record.get(userEntity.email))  // 필드명 맞추기
//						.national(record.get(userEntity.national))
//						.id(record.get(normalAuctionItem.id))
//						.build())
//				.collect(Collectors.toList());
//	}

	public Optional<NormalBidInfoDTO> findById(Long id) {
		
        QUserEntity buyer = new QUserEntity("buyer"); // 입찰자
        QUserEntity seller = new QUserEntity("seller"); // 판매자

	    // 쿼리 결과 가져오기
		List<Tuple> result = jpaQueryFactory
	            .select(
	                    normalBidInfo.id,
	                    normalBidInfo.auctionItemId,
	                    normalBidInfo.userInfoId,
	                    normalBidInfo.bidPrice,
	                    normalBidInfo.bidDate,
	                    
	                    normalAuctionItem.name,
	                    normalAuctionItem.description,
	                    
	                    buyer.name.as("bidderName"),
	                    buyer.email.as("bidderEmail"),          
	                    buyer.national.as("bidderNational"),
	                    
	                    seller.name.as("sellerName"),
	                    seller.email.as("sellerEmail"),
	                    seller.national.as("sellerNational"),
	                    seller.tel.as("sellerTel"),
	                    
	                    normalAuctionItem.startPrice,
	                    normalAuctionItemImage.path // 이미지 경로 추가
	                    
	            )
	            .from(normalBidInfo)
	            .innerJoin(normalAuctionItem).on(normalBidInfo.auctionItemId.eq(normalAuctionItem.id))
	            .innerJoin(buyer).on(normalBidInfo.userInfoId.eq(buyer.id)) // 입찰자
	            .innerJoin(seller).on(normalAuctionItem.userInfoId.eq(seller.id)) // 판매자
	            .leftJoin(normalAuctionItemImageList).on(normalAuctionItemImageList.normalAuctionItemId.eq(normalAuctionItem.id))
	            .leftJoin(normalAuctionItemImage).on(normalAuctionItemImageList.normalAuctionItemImageId.eq(normalAuctionItemImage.id))
	            .where(normalBidInfo.auctionItemId.eq(id))
	            .fetch(); // 여러 결과 반환

	    if (result.isEmpty()) {
	        return Optional.empty(); // 결과가 없으면 Optional.empty 반환
	    }

	    // 결과를 NormalBidInfoDTO로 변환
	    List<String> imagePaths = new ArrayList<>();
	    for (Tuple tuple : result) {
	        imagePaths.add(tuple.get(normalAuctionItemImage.path)); // 이미지 경로 리스트에 추가
	    }

	    // Tuple을 NormalBidInfoDTO로 변환하여 반환
	    NormalBidInfoDTO bidInfoDTO = NormalBidInfoDTO.builder()
	            .id(result.get(0).get(normalBidInfo.id))
	            .auctionItemId(result.get(0).get(normalBidInfo.auctionItemId))
	            .userInfoId(result.get(0).get(normalBidInfo.userInfoId))
	            .bidPrice(result.get(0).get(normalBidInfo.bidPrice))
	            .bidDate(result.get(0).get(normalBidInfo.bidDate))
	            .auctionItemName(result.get(0).get(normalAuctionItem.name))
	            .auctionItemDescription(result.get(0).get(normalAuctionItem.description))
	            
	            .bidderName(result.get(0).get(buyer.name)) // 입찰자 이름
	            .bidderEmail(result.get(0).get(buyer.email)) // 입찰자 이메일
	            .national(result.get(0).get(buyer.national)) // 입찰자 국가
	            .sellerName(result.get(0).get(seller.name)) // 판매자 이름
	            .sellerEmail(result.get(0).get(seller.email)) // 판매자 이메일
	            .sellerNational(result.get(0).get(seller.national)) // 판매자 국가
	            .sellerTel(result.get(0).get(seller.tel))	//판매자 번호..
	            
	            .normalAuctionItem(result.get(0).get(normalAuctionItem.id))
	            .startPrice(result.get(0).get(normalAuctionItem.startPrice))
	            .imagePaths(imagePaths) // 이미지 경로 리스트 설정
	            .build();

	    return Optional.of(bidInfoDTO); // Optional로 감싸서 반환
		}	


	}