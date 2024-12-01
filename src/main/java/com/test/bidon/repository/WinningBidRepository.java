package com.test.bidon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.bidon.entity.WinningBid;

@Repository
public interface WinningBidRepository extends JpaRepository<WinningBid, Long> {
	
    
	// 사용자 ID와 일반 경매 ID로 낙찰 정보 찾기
    Optional<WinningBid> findByUserInfoIdAndNormalBidIdIsNotNull(Long userInfoId);
    
    // 사용자 ID와 실시간 경매 ID로 낙찰 정보 찾기
    List<WinningBid> findByUserInfoIdAndLiveBidIdIsNotNull(Long userInfoId);
    
    // 일반 경매 ID로 낙찰 정보 찾기
    Optional<WinningBid> findByNormalBidId(Long normalBidId);
    
    // 실시간 경매 ID로 낙찰 정보 찾기
    Optional<WinningBid> findByLiveBidId(Long liveBidId);
    
    // 일반 경매 낙찰 목록
    List<WinningBid> findByNormalBidIdIsNotNull();
    
    // 실시간 경매 낙찰 목록
    List<WinningBid> findByLiveBidIdIsNotNull();   
	
    
    
}
