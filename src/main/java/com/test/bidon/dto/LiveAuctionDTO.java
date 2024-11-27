package com.test.bidon.dto;

import com.test.bidon.entity.LiveAuction;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LiveAuctionDTO {

    private Long id;
    private Long liveAuctionItemId;

    private LiveAuctionItemDTO liveAuctionItem;

    public LiveAuction toEntity() {
        return LiveAuction.builder()
                .id(this.getId())
                .liveAuctionItemId(this.getLiveAuctionItemId())
                .build();
    }
}
