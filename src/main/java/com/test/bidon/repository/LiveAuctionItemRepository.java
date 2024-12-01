package com.test.bidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.bidon.entity.LiveAuctionItem;

public interface LiveAuctionItemRepository extends JpaRepository<LiveAuctionItem, Long> {

}