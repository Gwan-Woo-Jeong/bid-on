package com.test.bidon.repository;

import com.test.bidon.entity.LiveAuctionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveAuctionItemRepository extends JpaRepository<LiveAuctionItem, Long> {

}
