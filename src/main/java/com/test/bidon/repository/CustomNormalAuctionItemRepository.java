package com.test.bidon.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.test.bidon.entity.QNormalAuctionItem.normalAuctionItem;
import static com.test.bidon.entity.QNormalAuctionItemImage.normalAuctionItemImage;
import static com.test.bidon.entity.QNormalAuctionItemImageList.normalAuctionItemImageList;
import static com.test.bidon.entity.QNormalBidInfo.normalBidInfo;

//Query DSL용 Repository
@Repository
@RequiredArgsConstructor
public class CustomNormalAuctionItemRepository {

    private final JPAQueryFactory jpaQueryFactory;
    /*
        boot-jpa/CustomAddressRepository.java 참고
     */

}
//<<<<<<< Updated upstream
//package com.test.bidon.repository;
//
//import org.springframework.stereotype.Repository;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//
//import lombok.RequiredArgsConstructor;
//
////Query DSL용 Repository
//@Repository
//@RequiredArgsConstructor
//public class CustomNormalAuctionItemRepository {
//
//    private final JPAQueryFactory jpaQueryFactory;
//
//}
//=======
//package com.test.bidon.repository;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
////Query DSL용 Repository
//@Repository
//@RequiredArgsConstructor
//public class CustomNormalAuctionItemRepository {
//
//    private final JPAQueryFactory jpaQueryFactory;
//
//}
//>>>>>>> Stashed changes
