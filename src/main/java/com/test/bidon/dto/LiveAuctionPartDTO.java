package com.test.bidon.dto;

import com.test.bidon.entity.LiveAuctionPart;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LiveAuctionPartDTO {

    private Long id;
    private Long userInfoId;
    private Long liveAuctionItemId;
    private LocalDateTime createTime;

    private UserInfoDTO userInfo;
    private LiveAuctionItemDTO liveAuctionItem;

    public LiveAuctionPart toEntity() {
        return LiveAuctionPart.builder()
                .id(this.getId())
                .userInfoId(this.getUserInfoId())
                .liveAuctionItemId(this.getLiveAuctionItemId())
                .createTime(this.getCreateTime())
                .build();
    }

}
