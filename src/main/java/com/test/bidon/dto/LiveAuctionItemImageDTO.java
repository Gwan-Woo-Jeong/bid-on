package com.test.bidon.dto;

import com.test.bidon.entity.LiveAuctionItemImage;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveAuctionItemImageDTO {
    
    private Long id;
    private String path;

    public LiveAuctionItemImage toEntity() {
        return LiveAuctionItemImage.builder()
                .id(this.getId())
                .path(this.getPath())
                .build();
    }

}
