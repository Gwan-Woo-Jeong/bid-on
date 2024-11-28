package com.test.bidon.dto;

import com.test.bidon.entity.LiveAuctionItem;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LiveAuctionItemDTO {

    private Long id;
    private Long userInfoId;
    private String name;
    private String description;
    private Integer startPrice;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;

    private UserInfoDTO userInfo;

    public LiveAuctionItem toEntity() {
        return LiveAuctionItem.builder()
                .id(this.getId())
                .userInfoId(this.getUserInfoId())
                .name(this.getName())
                .description(this.getDescription())
                .startPrice(this.getStartPrice())
                .startTime(this.getStartTime())
                .endTime(this.getEndTime())
                .build();
    }
    
    
    public LiveAuctionItemDTO(String name, LocalDateTime startTime, String userProfile, String userName, String userEmail) {
        this.name = name;
        this.startTime = startTime;
        this.userInfo = new UserInfoDTO(id, userProfile, userName, userEmail, userEmail, null, userEmail, userEmail, null, userEmail, startPrice, userEmail, null); 
    }


}