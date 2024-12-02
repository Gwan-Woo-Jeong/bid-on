package com.test.bidon.repository;

import static com.test.bidon.entity.QNormalAuctionItem.normalAuctionItem;
import static com.test.bidon.entity.QNormalBidInfo.normalBidInfo;
import static com.test.bidon.entity.QUserEntity.userEntity;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.bidon.dto.NormalUserFindDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NormalFindUserRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<NormalUserFindDTO> findUsersByAuctionItemId(Long id) {

        // 쿼리 결과 가져오기
        List<Tuple> result = jpaQueryFactory
                .select(
//                      normalBidInfo.id,
//                      normalBidInfo.auctionItemId,
//                      normalBidInfo.userInfoId,
//                      normalAuctionItem.name,
//                      normalAuctionItem.description,
//                      normalAuctionItem.startPrice,
//                		userEntity.email,        // 유저 이메일
//                		userEntity.tel           // 유저 전화번호
                		normalBidInfo.bidPrice,
                		normalBidInfo.bidDate,
                        userEntity.name,         // 유저 이름
                        userEntity.national     // 유저 국가
                )
                .from(normalBidInfo)
                .innerJoin(normalAuctionItem).on(normalBidInfo.auctionItemId.eq(normalAuctionItem.id)) // 경매 품목 조인
                .innerJoin(userEntity).on(normalBidInfo.userInfoId.eq(userEntity.id)) // 유저 엔티티 조인
                .where(normalBidInfo.auctionItemId.eq(id)) // 특정 경매 아이템 ID로 필터링
                .orderBy(normalBidInfo.bidDate.desc())
                .fetch(); // 여러 결과 반환

        if (result.isEmpty()) {
            return List.of(); // 결과가 없으면 빈 리스트 반환
        }

        // 결과를 NormalUserFindDTO로 변환
        return result.stream()
        	    .<NormalUserFindDTO>map(tuple -> NormalUserFindDTO.builder()
        	        .bidPrice(tuple.get(normalBidInfo.bidPrice))  // 입찰 가격
        	        .bidDate(tuple.get(normalBidInfo.bidDate))    // 입찰 날짜
        	        .name(tuple.get(userEntity.name))            // 유저 이름
        	        .national(tuple.get(userEntity.national))    // 유저 국가
        	        .build())
        	    .collect(Collectors.toList());


    }
}
