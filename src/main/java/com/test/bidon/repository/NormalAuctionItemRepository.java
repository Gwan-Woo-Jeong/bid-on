package com.test.bidon.repository;

import com.test.bidon.dto.NormalAuctionItemDTO;
import com.test.bidon.entity.NormalAuctionItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface NormalAuctionItemRepository extends JpaRepository<NormalAuctionItem, Long> {

//	@Query("SELECT new com.test.bidon.dto.NormalAuctionItemDTO(nai.name,  nai.status, nai.startTime, COUNT(naw.id)) " +
//		       "FROM NormalAuctionItem nai " +
//		       "LEFT JOIN NormalAuctionWish naw ON nai.id = naw.normalAuctionItem.id " +
//		       "WHERE nai.status IN ('진행중', '대기중') AND naw.id is not null " +
//		       "GROUP BY nai.name, nai.startTime, nai.status")
//		
//	List<NormalAuctionItemDTO> findWishItem();



}
