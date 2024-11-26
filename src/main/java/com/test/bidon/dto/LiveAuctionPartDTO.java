package com.test.bidon.dto;

import com.test.bidon.entity.LiveAuctionPart;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LiveAuctionPartDTO {

    private Long id;
    private Long userInfoId;
    private Long liveAuctionId;

    private UserInfoDTO userInfo;
    private LiveAuctionDTO liveAuction;

    public LiveAuctionPart toEntity() {
        return LiveAuctionPart.builder()
                .id(this.getId())
                .userInfoId(this.getUserInfoId())
                .liveAuctionId(this.getLiveAuctionId())
                .build();
    }

}
