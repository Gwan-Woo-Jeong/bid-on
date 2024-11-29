package com.test.bidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.bidon.entity.NormalAuctionItem;

public interface NormalAuctionItemRepository extends JpaRepository<NormalAuctionItem, Long> {


}
