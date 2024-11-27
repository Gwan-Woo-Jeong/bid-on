package com.test.bidon.dto;

import com.test.bidon.entity.LiveBidCost;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveBidCostDTO {

    private Long id;
    private Long liveAuctionPartId;
    private Long liveAuctionItemId;
    private Integer bidPrice;

    private LiveAuctionPartDTO liveAuctionPart;
    private LiveAuctionItemDTO liveAuctionItem;

    public LiveBidCost toEntity() {
        return LiveBidCost.builder()
                .id(this.getId())
                .liveAuctionPartId(this.getLiveAuctionPartId())
                .liveAuctionItemId(this.getLiveAuctionItemId())
                .bidPrice(this.getBidPrice())
                .build();
    }

}
