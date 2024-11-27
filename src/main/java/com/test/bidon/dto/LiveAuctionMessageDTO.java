package com.test.bidon.dto;

import com.test.bidon.entity.LiveAuctionMessage;
import com.test.bidon.entity.LiveAuctionPart;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LiveAuctionMessageDTO {

    private Long id;
    private Long liveAuctionPartId;
    private String type;
    private String content;

    private LiveAuctionPartDTO liveAuctionPart;

    public LiveAuctionMessage toEntity() {
        return LiveAuctionMessage.builder()
                .id(this.getId())
                .liveAuctionPartId(this.getLiveAuctionPartId())
                .type(this.getType())
                .content(this.getContent())
                .build();
    }

}
