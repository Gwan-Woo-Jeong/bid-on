package com.test.bidon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.test.bidon.dto.LiveAuctionItemDTO;
import com.test.bidon.dto.LiveAuctionItemListDTO;
import com.test.bidon.entity.LiveAuctionItem;

public interface LiveAuctionItemRepository extends JpaRepository<LiveAuctionItem, Long> {



}
