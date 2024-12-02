package com.test.bidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.bidon.entity.LiveAuctionItem;
import com.test.bidon.entity.LiveAuctionItemImage;

public interface LiveAuctionItemImageRepository extends JpaRepository<LiveAuctionItemImage, Long>{

}
