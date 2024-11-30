package com.test.bidon.repository;


import static com.querydsl.core.types.dsl.Expressions.numberTemplate;
import static com.test.bidon.entity.QLiveAuctionItem.liveAuctionItem;
import static com.test.bidon.entity.QLiveAuctionPart.liveAuctionPart;
import static com.test.bidon.entity.QLiveBidCost.liveBidCost;
import static com.test.bidon.entity.QNormalAuctionItem.normalAuctionItem;
import static com.test.bidon.entity.QNormalBidInfo.normalBidInfo;
import static com.test.bidon.entity.QUserEntity.userEntity;
import static com.test.bidon.entity.QWinningBid.winningBid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.bidon.dto.MonthlyAuctionStatusDTO;
import com.test.bidon.dto.MonthlyUserCountDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomAdminDashboardRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public long newUserCount() {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        return jpaQueryFactory
                .select(userEntity.count())
                .from(userEntity)
                .where(userEntity.createDate.goe(thirtyDaysAgo))
                .fetchOne();
    }
	
	public List<MonthlyUserCountDTO> findMonthlyUserCounts() {
		// 월별 신규 회원 수를 계산
        List<MonthlyUserCountDTO> results = jpaQueryFactory
            .select(
                Projections.constructor(MonthlyUserCountDTO.class,
                    userEntity.createDate.month().castToNum(Integer.class).stringValue(),
                    userEntity.id.count()
                )
            )
            .from(userEntity)
            .groupBy(userEntity.createDate.month())
            .orderBy(userEntity.createDate.month().asc())
            .fetch();

        // 모든 월에 대한 데이터 생성
        List<MonthlyUserCountDTO> completeResults = new ArrayList<>();
        long cumulativeCount = 0;

        // 1부터 12까지의 모든 월에 대해 처리
        for (int i = 1; i <= 12; i++) {
            final int currentMonth = i;  // 새로운 final 변수 생성
            
            // 해당 월의 데이터 찾기
            MonthlyUserCountDTO monthData = results.stream()
                .filter(r -> Integer.parseInt(r.getMonth()) == currentMonth)  // currentMonth 사용
                .findFirst()
                .orElse(new MonthlyUserCountDTO(String.valueOf(currentMonth), 0L, cumulativeCount));

            // 새로운 가입자가 있는 경우에만 누적 회원 수 증가
            if (monthData.getCount() > 0) {
                cumulativeCount += monthData.getCount();
            }
            
            // 누적 회원 수 설정 (데이터가 없는 달도 이전 달의 누적값 유지)
            monthData.setCumulativeCount(cumulativeCount);
            completeResults.add(monthData);
        }

        return completeResults;
    }
	


	public long getOngoingNormalAuctionItemCount() {
		return jpaQueryFactory
				.selectFrom(normalAuctionItem)
				.where(normalAuctionItem.status.eq("진행중"))
				.fetchCount();
	}

	public long getOngoingLiveAuctionItemCount() {
		return jpaQueryFactory
				.selectFrom(liveAuctionItem)
				.where(liveAuctionItem.startTime.goe(LocalDateTime.now())
						.and(liveAuctionItem.endTime.isNull()))
				.fetchCount();
	}

	public long getTotalWinningBidCount() {
		return jpaQueryFactory
				.selectFrom(winningBid)
				.fetchCount();
	}
	
	public long getTotalBidPrice() {
		NumberExpression<Long> totalBidPrice = numberTemplate(Long.class,
                "COALESCE(SUM({0}), 0) + COALESCE(SUM({1}), 0)",
                normalBidInfo.bidPrice, liveBidCost.bidPrice);

        return jpaQueryFactory
                .select(totalBidPrice)
                .from(winningBid)
                .leftJoin(normalBidInfo).on(winningBid.normalBidId.eq(normalBidInfo.id))
                .leftJoin(liveBidCost).on(winningBid.liveBidId.eq(liveBidCost.id))
                .fetchOne();
    }


	public Long getBidEnterUserCount() {
	    Set<Long> uniqueUserIds = new HashSet<>();
	    
	    // 각각의 테이블에서 userInfoId를 조회
	    uniqueUserIds.addAll(jpaQueryFactory
	        .select(normalAuctionItem.userInfoId)
	        .from(normalAuctionItem)
	        .fetch());
	        
	    uniqueUserIds.addAll(jpaQueryFactory
	        .select(normalBidInfo.userInfoId)
	        .from(normalBidInfo)
	        .fetch());
	    
	    uniqueUserIds.addAll(jpaQueryFactory
		        .select(liveAuctionPart.userInfoId)
		        .from(liveAuctionPart)
		        .fetch());
	    
	    uniqueUserIds.addAll(jpaQueryFactory
		        .select(liveAuctionItem.userInfoId)
		        .from(liveAuctionItem)
		        .fetch());
	    
	    return (long) uniqueUserIds.size();
	}

	public Double getAverageBidPrice() {
        
        NumberExpression<Double> normalBidAvg = normalBidInfo.bidPrice.avg().coalesce(0.0);
        NumberExpression<Double> liveBidAvg = liveBidCost.bidPrice.avg().coalesce(0.0);
        
        return jpaQueryFactory
            .select(normalBidAvg.add(liveBidAvg).multiply(0.1))
            .from(winningBid)
            .leftJoin(normalBidInfo).on(winningBid.normalBidId.eq(normalBidInfo.id))
            .leftJoin(liveBidCost).on(winningBid.liveBidId.eq(liveBidCost.id))
            .fetchOne();
    }
	
	private List<String> getAllMonths() {
        List<String> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(String.format("%02d", i));
        }
        return months;
    }
    

//	public List<MonthlyAuctionStatusDTO> getMonthlyAuctionStats() {
//		 StringTemplate monthFormat = (StringTemplate) Expressions.stringTemplate(
//		            "to_char({0}, 'MM')", normalAuctionItem.startTime);
//		        StringTemplate liveMonthFormat = (StringTemplate) Expressions.stringTemplate(
//		            "to_char({0}, 'MM')", liveAuctionItem.startTime);
//		            
//		        // 일반 경매 등록 수
//		        Map<String, Long> normalRegistered = jpaQueryFactory
//		            .select(monthFormat, normalAuctionItem.count())
//		            .from(normalAuctionItem)
//		            .groupBy(monthFormat)
//		            .fetch()
//		            .stream()
//		            .collect(Collectors.toMap(
//		                tuple -> tuple.get(0, String.class),
//		                tuple -> tuple.get(1, Long.class)
//		            ));
//		            
//		        // 실시간 경매 등록 수
//		        Map<String, Long> liveRegistered = jpaQueryFactory
//		            .select(liveMonthFormat, liveAuctionItem.count())
//		            .from(liveAuctionItem)
//		            .groupBy(liveMonthFormat)
//		            .fetch()
//		            .stream()
//		            .collect(Collectors.toMap(
//		                tuple -> tuple.get(0, String.class),
//		                tuple -> tuple.get(1, Long.class)
//		            ));
//		            
//		        // 진행중인 일반 경매
//		        Map<String, Long> normalInProgress = jpaQueryFactory
//		            .select(monthFormat, normalAuctionItem.count())
//		            .from(normalAuctionItem)
//		            .where(normalAuctionItem.status.eq("진행중"))
//		            .groupBy(monthFormat)
//		            .fetch()
//		            .stream()
//		            .collect(Collectors.toMap(
//		                tuple -> tuple.get(0, String.class),
//		                tuple -> tuple.get(1, Long.class)
//		            ));
//		            
//		        // 진행중인 실시간 경매
//		        Map<String, Long> liveInProgress = jpaQueryFactory
//		            .select(liveMonthFormat, liveAuctionItem.count())
//		            .from(liveAuctionItem)
//		            .where(liveAuctionItem.startTime.goe(Expressions.currentTimestamp())
//		                .and(liveAuctionItem.endTime.isNull()))
//		            .groupBy(liveMonthFormat)
//		            .fetch()
//		            .stream()
//		            .collect(Collectors.toMap(
//		                tuple -> tuple.get(0, String.class),
//		                tuple -> tuple.get(1, Long.class)
//		            ));
//		            
//		        List<MonthlyAuctionStatusDTO> result = new ArrayList<>();
//		        for (String month : getAllMonths()) {
//		            result.add(new MonthlyAuctionStatusDTO(
//		                month,
//		                normalRegistered.getOrDefault(month, 0L),
//		                liveRegistered.getOrDefault(month, 0L),
//		                normalInProgress.getOrDefault(month, 0L),
//		                liveInProgress.getOrDefault(month, 0L),
//		                normalRegistered.getOrDefault(month, 0L) - normalInProgress.getOrDefault(month, 0L),
//		                liveRegistered.getOrDefault(month, 0L) - liveInProgress.getOrDefault(month, 0L)
//		            ));
//		        }
//		        
//		        return result;
//	}



}
