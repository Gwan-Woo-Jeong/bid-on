package com.test.bidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.test.bidon.entity.NormalBidInfo;
import java.util.List;
import java.util.Optional;

public interface NormalBidInfoRepository extends JpaRepository<NormalBidInfo, Long> {
    
    // 특정 경매 아이템의 최신 입찰 정보 조회
    Optional<NormalBidInfo> findTopByAuctionItemIdOrderByBidDateDesc(Long auctionItemId);
    
    // 사용자 ID로 입찰 정보 찾기(사용자가 입찰한 일반 경매 목록 조회)
    List<NormalBidInfo> findByUserInfoId(Long userInfoId);

	int countByUserInfoId(Long userInfoId);
}