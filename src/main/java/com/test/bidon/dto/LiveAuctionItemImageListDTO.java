package com.test.bidon.dto;

import com.test.bidon.entity.LiveAuctionItemImageList;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveAuctionItemImageListDTO {
    
    private Long id;
    private Long liveAuctionItemImageId;
    private Long liveAuctionItemId;
    private Integer isMainImage;

    private LiveAuctionItemImageDTO liveAuctionItemImage;
    private LiveAuctionItemDTO liveAuctionItem;

    public LiveAuctionItemImageList toEntity() {
        return LiveAuctionItemImageList.builder()
                .id(this.getId())
                .liveAuctionItemImageId(this.getLiveAuctionItemImageId())
                .liveAuctionItemId(this.getLiveAuctionItemId())
                .isMainImage(this.getIsMainImage())
                .build();
    }

}
