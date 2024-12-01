package com.test.bidon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.bidon.entity.LiveBidCost;

@Repository
public interface LiveBidCostRepository extends JpaRepository<LiveBidCost, Long> {
    Optional<LiveBidCost> findTopByLiveAuctionItemIdOrderByBidTimeDesc(Long itemId);
	List<LiveBidCost> findByLiveAuctionPartUserInfoId(Long userId);
	int countByLiveAuctionPartUserInfoId(Long userId);
}
