package com.test.bidon.repository;

import static com.test.bidon.entity.QLiveAuctionItem.liveAuctionItem;
import static com.test.bidon.entity.QLiveAuctionItemImage.liveAuctionItemImage;
import static com.test.bidon.entity.QLiveAuctionItemImageList.liveAuctionItemImageList;
import static com.test.bidon.entity.QUserEntity.userEntity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.bidon.dto.LiveAuctionItemDTO;
import com.test.bidon.dto.LiveAuctionItemListDTO;
import com.test.bidon.entity.LiveAuctionItem;
import com.test.bidon.entity.QUserEntity;
import com.test.bidon.entity.UserEntity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LiveAuctionItemRepository {
	
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


	public List<LiveAuctionItem> findLiveList() {
		
		return jpaQueryFactory
				.selectFrom(liveAuctionItem)
				.orderBy(liveAuctionItem.startTime.desc())
				.fetch();
	}

	@ManyToOne
	@JoinColumn(name="userInfoId")
	private UserEntity userInfo;

	public List<LiveAuctionItemDTO> findProgress() {
	    return jpaQueryFactory
	            .select(Projections.constructor(
	                    LiveAuctionItemDTO.class,
	                    liveAuctionItem.name,            // 물품명
	                    liveAuctionItem.startTime,       // 경매 시작 시간
	                    userEntity.profile,         	 // 유저의 프로필 사진
	                    userEntity.name,                 // 유저의 이름
	                    userEntity.email                 // 유저의 이메일
	            ))
	            .from(liveAuctionItem)                    // 주 테이블: 경매 아이템
	            .join(liveAuctionItem.userInfo, userEntity) // 조인: userInfo (유저 정보)
	            .where(liveAuctionItem.startTime.after(LocalDateTime.now())) // 진행중인 경매만 가져오기
	            .fetch();  // 결과 조회
	}



}
