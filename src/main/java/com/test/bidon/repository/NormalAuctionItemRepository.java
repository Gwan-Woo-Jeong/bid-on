package com.test.bidon.repository;

import com.test.bidon.entity.NormalAuctionItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalAuctionItemRepository extends JpaRepository<NormalAuctionItem, Long> {

	List<NormalAuctionItem> findByUserInfoId(Long userInfoId);	//혜미가 추가함

}
