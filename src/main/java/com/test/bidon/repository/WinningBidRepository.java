package com.test.bidon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.test.bidon.entity.WinningBid;

@Repository
public interface WinningBidRepository extends JpaRepository<WinningBid, Long> {

    // 실시간 경매 ID와 최종 낙찰 ID로 실제 존재 여부 확인
    @Query("SELECT COUNT(*) FROM WinningBid wb JOIN LiveBidCost lc ON wb.liveBidId = lc.id WHERE wb.id = :winningBidId AND lc.liveAuctionItemId = :liveAuctionItemId AND wb.userInfoId = :userId")
    int countByWinBidIdAndLiveItemId(@Param("winningBidId") Long winningBidId, @Param("liveAuctionItemId") Long liveAuctionItemId, @Param("userId") Long userId);

    // 일반 경매 ID와 최종 낙찰 ID로 실제 존재 여부 확인
    @Query("SELECT COUNT(*) FROM WinningBid wb JOIN NormalBidInfo nb ON wb.normalBidId = nb.id WHERE wb.id = :winningBidId AND nb.auctionItemId = :auctionItemId AND wb.userInfoId = :userId")
    int countByWinBidIdAndNormalItemId(@Param("winningBidId") Long winningBidId, @Param("auctionItemId") Long auctionItemId, @Param("userId") Long userId);

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

    int countByUserInfoIdAndNormalBidIdIsNotNull(Long userInfoId);

    int countByUserInfoIdAndLiveBidIdIsNotNull(Long userInfoId);

    // WinningBidRepository에 추가할 메서드
    List<WinningBid> findAllByUserInfoIdAndNormalBidIdIsNotNull(Long userInfoId);

    List<WinningBid> findAllByUserInfoIdAndLiveBidIdIsNotNull(Long userInfoId);

}
