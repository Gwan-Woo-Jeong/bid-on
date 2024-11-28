package com.test.bidon.repository;

import static com.test.bidon.entity.QUserEntity.userEntity; // Q타입 클래스의 경로

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.bidon.dto.MonthlyUserCountDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AdminDashboardRepository {

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
	                userEntity.createDate.month().castToNum(Integer.class).stringValue(), // 월
	                userEntity.id.count() // 신규 회원 수
	            )
	        )
	        .from(userEntity)
	        .groupBy(userEntity.createDate.month())
	        .orderBy(userEntity.createDate.month().asc()) // 월별로 오름차순 정렬
	        .fetch();

	    // 누적 회원 수 계산
	    long cumulativeCount = 0; // long으로 변경
	    for (MonthlyUserCountDTO result : results) {
	        cumulativeCount += result.getCount();
	        result.setCumulativeCount(cumulativeCount);
	    }

	    return results; // MonthlyUserCountDTO 리스트 반환
	}
	

}
