package com.test.bidon.repository;


import static com.querydsl.core.types.dsl.Expressions.numberTemplate;
import static com.test.bidon.entity.QLiveAuctionItem.liveAuctionItem;
import static com.test.bidon.entity.QLiveAuctionPart.liveAuctionPart;
import static com.test.bidon.entity.QLiveBidCost.liveBidCost;
import static com.test.bidon.entity.QNormalAuctionItem.normalAuctionItem;
import static com.test.bidon.entity.QNormalBidInfo.normalBidInfo;
import static com.test.bidon.entity.QSubCategory.subCategory;
import static com.test.bidon.entity.QUserEntity.userEntity;
import static com.test.bidon.entity.QWinningBid.winningBid;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.bidon.dto.MonthlyAvgtStartBidPriceDTO;
import com.test.bidon.dto.MonthlyItemCountDTO;
import com.test.bidon.dto.MonthlyRevenueDTO;
import com.test.bidon.dto.MonthlyUserCountDTO;
import com.test.bidon.dto.QuarterlyRevenueDTO;
import com.test.bidon.dto.SubCategoryCountDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomAdminDashboardRepository {

	private final JPAQueryFactory jpaQueryFactory;
	@PersistenceContext
	private EntityManager entityManager;
//	@Autowired
//    private EntityManager em;
	
	// Date를 LocalDateTime으로 변환하는 메서드
    private LocalDateTime convertDateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

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
	

	//월별 경매 물품 분석 리스트(진행중,종료된,등록된 경매)	
	public List<MonthlyItemCountDTO> findMonthlyItemCountList() {
        String nativeQuery = "WITH MONTHS AS (\r\n"
        		+ "    SELECT TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'YEAR'), LEVEL-1), 'MM') AS MONTH\r\n"
        		+ "    FROM DUAL\r\n"
        		+ "    CONNECT BY LEVEL <= 12\r\n"
        		+ ")\r\n"
        		+ "SELECT \r\n"
        		+ "    M.MONTH,\r\n"
        		+ "    COALESCE(R.REGISTERED_COUNT, 0) AS REGISTERED_COUNT,\r\n"
        		+ "    COALESCE(O.ONGOING_COUNT, 0) AS ONGOING_COUNT,\r\n"
        		+ "    COALESCE(E.ENDED_COUNT, 0) AS ENDED_COUNT\r\n"
        		+ "FROM MONTHS M\r\n"
        		+ "LEFT JOIN (\r\n"
        		+ "    SELECT TO_CHAR(startTime, 'MM') AS MONTH,\r\n"
        		+ "           COUNT(*) AS REGISTERED_COUNT\r\n"
        		+ "    FROM (\r\n"
        		+ "        SELECT startTime FROM NormalAuctionItem\r\n"
        		+ "        UNION ALL\r\n"
        		+ "        SELECT startTime FROM LiveAuctionItem\r\n"
        		+ "    )\r\n"
        		+ "    GROUP BY TO_CHAR(startTime, 'MM')\r\n"
        		+ ") R ON M.MONTH = R.MONTH\r\n"
        		+ "LEFT JOIN (\r\n"
        		+ "    SELECT TO_CHAR(startTime, 'MM') AS MONTH,\r\n"
        		+ "           COUNT(*) AS ONGOING_COUNT\r\n"
        		+ "    FROM (\r\n"
        		+ "        SELECT startTime FROM NormalAuctionItem WHERE status = '진행중' or status = '대기중'"
        		+ "        UNION ALL\r\n"
        		+ "        SELECT startTime FROM LiveAuctionItem WHERE startTime >= SYSDATE AND endTime IS NULL\r\n"
        		+ "    )\r\n"
        		+ "    GROUP BY TO_CHAR(startTime, 'MM')\r\n"
        		+ ") O ON M.MONTH = O.MONTH\r\n"
        		+ "LEFT JOIN (\r\n"
        		+ "    SELECT TO_CHAR(startTime, 'MM') AS MONTH,\r\n"
        		+ "           COUNT(*) AS ENDED_COUNT\r\n"
        		+ "    FROM (\r\n"
        		+ "        SELECT startTime FROM NormalAuctionItem WHERE status = '종료'\r\n"
        		+ "        UNION ALL\r\n"
        		+ "        SELECT startTime FROM LiveAuctionItem WHERE startTime <= SYSDATE AND endTime IS NOT NULL\r\n"
        		+ "    )\r\n"
        		+ "    GROUP BY TO_CHAR(startTime, 'MM')\r\n"
        		+ ") E ON M.MONTH = E.MONTH\r\n"
        		+ "ORDER BY M.MONTH";
        
        
        Query query = entityManager.createNativeQuery(nativeQuery);
        
        List<Object[]> results = query.getResultList();

        return results.stream()
            .map(result -> new MonthlyItemCountDTO(
                (String) result[0],
                ((Number) result[1]).longValue(),
                ((Number) result[2]).longValue(),
                ((Number) result[3]).longValue()
            ))
            .collect(Collectors.toList());
    }
	
	 
	    public List<MonthlyRevenueDTO> getMonthlyRevenueList() {
	        String nativeQuery = """
	            WITH MONTHS AS (
	                SELECT TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'YEAR'), LEVEL-1), 'MM') AS MONTH
	                FROM DUAL
	                CONNECT BY LEVEL <= 12
	            ), NormalRevenue AS (
	                SELECT 
	                    TO_CHAR(nbi.bidDate, 'MM') AS month,
	                    SUM(nbi.bidprice) * 0.1 AS monthly_revenue
	                FROM 
	                    winningbid wb
	                FULL OUTER JOIN 
	                    NormalBidInfo nbi ON wb.normalBidId = nbi.id
	                WHERE 
	                    nbi.bidDate IS NOT NULL
	                GROUP BY 
	                    TO_CHAR(nbi.bidDate, 'MM')
	            ), LiveRevenue AS (
	                SELECT 
	                    TO_CHAR(lbc.bidTime, 'MM') AS month,
	                    SUM(lbc.bidprice) * 0.1 AS monthly_revenue
	                FROM 
	                    winningbid wb
	                FULL OUTER JOIN 
	                    LiveBidCost lbc ON wb.liveBidId = lbc.id
	                WHERE 
	                    lbc.bidTime IS NOT NULL
	                GROUP BY 
	                    TO_CHAR(lbc.bidTime, 'MM')
	            ), TotalRevenue AS (
	                SELECT 
	                    M.MONTH,
	                    NVL(NR.monthly_revenue, 0) + NVL(LR.monthly_revenue, 0) AS monthlyTotalRevenue
	                FROM 
	                    MONTHS M
	                LEFT JOIN 
	                    NormalRevenue NR ON M.MONTH = NR.month
	                LEFT JOIN 
	                    LiveRevenue LR ON M.MONTH = LR.month
	            )
	            SELECT 
	                MONTH,
	                monthlyTotalRevenue,
	                SUM(monthlyTotalRevenue) OVER (ORDER BY MONTH) AS cumulativeTotalRevenue
	            FROM 
	                TotalRevenue
	            ORDER BY 
	                MONTH
	        """;

	        Query query = entityManager.createNativeQuery(nativeQuery);
	        List<Object[]> results = query.getResultList();

	        return results.stream()
	            .map(result -> new MonthlyRevenueDTO(
	                (String) result[0],
	                ((Number) result[1]).longValue(),
	                ((Number) result[2]).longValue()
	            ))
	            .collect(Collectors.toList());
	    }
	    
	    
	    //서브카테고리별 4개만 가져오기
	    public List<SubCategoryCountDTO> findTop4SubCategories() {
	        //QSubCategory subCategory = QSubCategory.subCategory;
	        //QNormalAuctionItem normalAuctionItem = QNormalAuctionItem.normalAuctionItem;
	        
	        return jpaQueryFactory
	            .select(Projections.constructor(SubCategoryCountDTO.class,
	                subCategory.name,
	                normalAuctionItem.count()))
	            .from(subCategory)
	            .innerJoin(normalAuctionItem)
	            .on(subCategory.id.eq(normalAuctionItem.categorySubId))
	            .groupBy(subCategory.name, subCategory.id)
	            .orderBy(subCategory.id.asc())
	            .limit(4)
	            .fetch();
	    }
	    
	    //분기별 일반, 실시간 경매 수익금 View 불러오기
	    public List<QuarterlyRevenueDTO> getQuarterlyRevenue() {
	        // View를 조회하는 네이티브 쿼리 사용
	        String sql = "SELECT * FROM quarterly_revenue_view ORDER BY Quarter";
	        Query query = entityManager.createNativeQuery(sql);
	        
	        List<Object[]> results = query.getResultList();
	        
	        return results.stream()
	            .map(result -> QuarterlyRevenueDTO.builder()
	                .quarter(((Number) result[0]).intValue())
	                .normalRevenue(((Number) result[1]).doubleValue())
	                .liveRevenue(((Number) result[2]).doubleValue())
	                .build())
	            .collect(Collectors.toList());
	    }
	    
	    // 월별 평균 시작 금액 + 평균 낙찰 금액
	    public List<MonthlyAvgtStartBidPriceDTO> getMonthlyAvgtStartBidPriceList() {
	        // View를 조회하는 네이티브 쿼리 사용
	        String sql = "SELECT * FROM monthly_avg_start_bid_price_view ORDER BY month";
	        Query query = entityManager.createNativeQuery(sql);

	        List<Object[]> results = query.getResultList();

	        return results.stream().map(result -> {
	            MonthlyAvgtStartBidPriceDTO dto = new MonthlyAvgtStartBidPriceDTO();
	            dto.setMonth((String) result[0]);
	            
	            // BigDecimal을 Double로 변환
	            dto.setAvgStartPrice(((BigDecimal) result[1]).doubleValue());
	            dto.setAvgBidPrice(((BigDecimal) result[2]).doubleValue());
	            
	            return dto;
	        }).collect(Collectors.toList());
	    }
	    
}





