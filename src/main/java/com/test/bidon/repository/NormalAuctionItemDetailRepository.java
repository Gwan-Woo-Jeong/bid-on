package com.test.bidon.repository;

import static com.test.bidon.entity.QNormalAuctionItem.normalAuctionItem;  // QNormalAuctionItem 임포트
import static com.test.bidon.entity.QNormalAuctionItemImage.normalAuctionItemImage;
import static com.test.bidon.entity.QNormalAuctionItemImageList.normalAuctionItemImageList;
import static com.test.bidon.entity.QNormalBidInfo.normalBidInfo;
import static com.test.bidon.entity.QUserEntity.userEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.bidon.dto.NormalAuctionItemWithImgDTO;
import com.test.bidon.dto.NormalBidInfoDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NormalAuctionItemDetailRepository {

	private final JPAQueryFactory jpaQueryFactory;


	public Optional<NormalBidInfoDTO> findById(Long id) {
		
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
	                    normalAuctionItem.startPrice,
	                    userEntity.name,
	                    userEntity.email,
	                    userEntity.national,
	                    userEntity.tel,
	                    userEntity.createDate
	            )
	            .from(normalBidInfo)
	            .innerJoin(normalAuctionItem).on(normalBidInfo.auctionItemId.eq(normalAuctionItem.id))
	            .innerJoin(userEntity).on(normalBidInfo.userInfoId.eq(userEntity.id))  // userInfoId와 userEntity.id로 조인
	            .where(normalBidInfo.auctionItemId.eq(id))
	            .fetch();// 여러 결과 반환
		
		

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
	            .normalAuctionItem(result.get(0).get(normalAuctionItem.id))
	            .startPrice(result.get(0).get(normalAuctionItem.startPrice))
	            .name(result.get(0).get(userEntity.name))
	            .email(result.get(0).get(userEntity.email))
	            .national(result.get(0).get(userEntity.national))
	            .tel(result.get(0).get(userEntity.tel))
	            .createDate(result.get(0).get(userEntity.createDate))
	            
	            .build();

	    return Optional.of(bidInfoDTO); // Optional로 감싸서 반환
		}
	
	
//사진사진사진사진사진사진사진사진
	public List<NormalAuctionItemWithImgDTO> findItemImg() {
		List<Tuple> results = jpaQueryFactory
				.select(
						normalAuctionItem.id, 
						normalAuctionItem.name, 
						normalAuctionItemImage.path
						)
				.from(normalAuctionItemImageList)
                .join(normalAuctionItemImageList.normalAuctionItem, normalAuctionItem)
                .join(normalAuctionItemImageList.normalAuctionItemImage, normalAuctionItemImage)
                .fetch();
        return results.stream()
                .collect(Collectors.groupingBy(
                    tuple -> tuple.get(0, Long.class),  // 아이템 ID를 기준으로 그룹화
                    Collectors.mapping(
                        tuple -> tuple.get(2, String.class),  // 이미지 URL을 리스트로 수집
                        Collectors.toList()
                    )
                ))
                .entrySet().stream()  // 그룹화된 결과를 스트림으로 변환
                .map(entry -> new NormalAuctionItemWithImgDTO(entry.getKey(), "아이템 이름", entry.getValue()))
                .collect(Collectors.toList());
        }
	
	


	
	
}
	
