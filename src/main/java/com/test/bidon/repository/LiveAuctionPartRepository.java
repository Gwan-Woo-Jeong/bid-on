package com.test.bidon.repository;

import com.test.bidon.entity.LiveAuctionPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LiveAuctionPartRepository extends JpaRepository<LiveAuctionPart, Long> {
    Optional<LiveAuctionPart> findFirstByUserInfoIdAndLiveAuctionItemIdOrderByCreateTimeDesc(Long userInfoId, Long liveAuctionItemId);
}
