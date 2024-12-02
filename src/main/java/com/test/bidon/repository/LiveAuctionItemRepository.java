package com.test.bidon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.bidon.entity.LiveAuctionItem;

public interface LiveAuctionItemRepository extends JpaRepository<LiveAuctionItem, Long> {

	List<LiveAuctionItem> findByUserInfoId(Long userInfoId);	//-HM-

	int countByUserInfoId(Long userInfoId);	//-HM-

}