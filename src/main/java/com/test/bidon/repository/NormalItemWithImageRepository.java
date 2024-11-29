//package com.test.bidon.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.test.bidon.dto.NormalItemWithImageDTO;
//import com.test.bidon.entity.NormalAuctionItemImageList;
//
//@Repository
//public class NormalItemWithImageRepository extends JpaRepository<NormalAuctionItemImageList, Long> {
//
//    @Query("SELECT new com.test.bidon.dto.NormalItemWithImageDTO(" +
//            "nai.id, nai.normalAuctionItemImage.id, nai.normalAuctionItem.id, nai.normalAuctionItemImage.path, nai.isMainImage) " +
//            "FROM NormalAuctionItemImageList nai " +
//            "JOIN nai.normalAuctionItem " +
//            "JOIN nai.normalAuctionItemImage " +
//            "WHERE nai.normalAuctionItem.id = :auctionItemId")
//    List<NormalItemWithImageDTO> findItemImagesByAuctionItemId(Long auctionItemId);
//}
