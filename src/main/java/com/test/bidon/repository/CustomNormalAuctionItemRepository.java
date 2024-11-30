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

    // 대표이미지 값이 1이고, 경매 상태가 진행중이거나 대기중인 tuple만 골라서 browse-bid 페이지에 출력하는 method
    // offset과 limit으로 Controller에서 페이징 구현
    public List<Tuple> joinTablesToDisplayBrowseBid(int offset, int limit) {

        return jpaQueryFactory
                .select(normalAuctionItem.id, normalAuctionItem.name, normalAuctionItemImage.path)
                .from(normalAuctionItemImageList)
                .join(normalAuctionItemImageList.normalAuctionItem, normalAuctionItem)
                .join(normalAuctionItemImageList.normalAuctionItemImage, normalAuctionItemImage)
                .where(normalAuctionItemImageList.isMainImage.eq(1L)
                        .and(normalAuctionItem.status.eq("진행중")
                                .or(normalAuctionItem.status.eq("대기중")))
                )
                .orderBy(normalAuctionItem.endTime.asc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    // List<Tuple>의 Tuple 개수를 세는 method
    public long count(int offset, int limit) {
        return jpaQueryFactory
                .select(normalAuctionItem.id, normalAuctionItem.name, normalAuctionItemImage.path)
                .from(normalAuctionItemImageList)
                .join(normalAuctionItemImageList.normalAuctionItem, normalAuctionItem)
                .join(normalAuctionItemImageList.normalAuctionItemImage, normalAuctionItemImage)
                .where(normalAuctionItemImageList.isMainImage.eq(1L)
                        .and(normalAuctionItem.status.eq("진행중")
                                .or(normalAuctionItem.status.eq("대기중")))
                )
                .orderBy(normalAuctionItem.endTime.asc())
                .offset(offset)
                .limit(limit)
                .fetchCount();
    }

    // 경매 물품의 종료 시간과 현재 최고 입찰가를 불러오는 method
    public List<Tuple> getTimeAndPrice() {
                /*
                    SELECT auc.id, auc.name, auc.endTime, MAX(bidPrice) AS maxPrice
                    FROM NormalAuctionItem auc
                    INNER JOIN NormalBidInfo bid
                        ON bid.auctionItemId = auc.id
                    GROUP BY auc.id, auc.name, auc.endTime;
                    ...를 Query DSL 방식으로 refactoring 필요.
                */
        return jpaQueryFactory
                .select(normalAuctionItem.id, normalAuctionItem.name, normalAuctionItem.endTime, normalBidInfo.bidPrice.max())
                .from(normalBidInfo)
                .join(normalBidInfo.normalAuctionItem, normalAuctionItem)
                .groupBy(normalAuctionItem.id, normalAuctionItem.name, normalAuctionItem.endTime)
                .orderBy(normalAuctionItem.endTime.asc())
                .fetch();
    }

}