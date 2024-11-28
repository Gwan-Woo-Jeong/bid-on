package com.test.bidon.repository;

import com.test.bidon.entity.NormalAuctionItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NormalAuctionItemRepository extends JpaRepository<NormalAuctionItem, Long> {

	
	 @Query("SELECT nai FROM NormalAuctionItem nai " +
	           "LEFT JOIN NormalAuctionWish naw ON nai.id = naw.normalAuctionItem.id " +
	           "WHERE nai.status IN ('진행중', '대기중') " +
	           "GROUP BY nai.id")
	List<NormalAuctionItem> findItemsWithWishCount();

}
