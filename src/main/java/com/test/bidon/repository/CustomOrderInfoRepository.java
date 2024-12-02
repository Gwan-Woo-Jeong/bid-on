package com.test.bidon.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.bidon.dto.OrderItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.test.bidon.entity.QLiveAuctionItem.liveAuctionItem;
import static com.test.bidon.entity.QLiveAuctionItemImage.liveAuctionItemImage;
import static com.test.bidon.entity.QLiveAuctionItemImageList.liveAuctionItemImageList;
import static com.test.bidon.entity.QLiveBidCost.liveBidCost;
import static com.test.bidon.entity.QOrderInfo.orderInfo;
import static com.test.bidon.entity.QWinningBid.winningBid;

@Repository
@RequiredArgsConstructor
public class CustomOrderInfoRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public OrderItemDTO getOrderInfoWithAuctionItem(Long orderId) {
        return jpaQueryFactory
                .select(Projections.fields(
                        OrderItemDTO.class,
                        liveAuctionItem.id.as("itemId"),
                        liveAuctionItem.name.as("itemName"),
                        liveAuctionItemImage.path.as("itemMainImagePath"),
                        orderInfo.createDate.as("orderCreateDate"),
                        orderInfo.address.as("orderAddress"),
                        orderInfo.detailAddress.as("orderDetailAddress"),
                        orderInfo.country.as("orderCountry"),
                        orderInfo.city.as("orderCity"),
                        orderInfo.district.as("orderDistrict")
                )).from(orderInfo)
                .join(orderInfo.winningBid, winningBid)
                .join(winningBid.liveBidCost, liveBidCost)
                .join(liveBidCost.liveAuctionItem, liveAuctionItem)
                .join(liveAuctionItemImageList).on(liveAuctionItem.id.eq(liveAuctionItemImageList.liveAuctionItem.id))
                .join(liveAuctionItemImage).on(liveAuctionItemImageList.liveAuctionItemImage.id.eq(liveAuctionItemImage.id))
                .where(orderInfo.id.eq(orderId))
                .fetchFirst();
    }

}

