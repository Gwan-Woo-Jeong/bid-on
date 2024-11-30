package com.test.bidon.repository;

import com.test.bidon.entity.LiveAuctionPartSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LiveAuctionPartRepository extends JpaRepository<LiveAuctionPartSummary, Long> {
    Optional<LiveAuctionPartSummary> findFirstByUserInfoIdAndLiveAuctionItemIdOrderByCreateTimeDesc(Long userInfoId, Long liveAuctionItemId);
}
