package com.test.bidon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveBidRoomItemDTO {

    private Long itemId;
    private String itemName;
    private String itemMainImagePath;
    private Integer startPrice;
    private LocalDateTime startTime;
    private String description;
    private String brand;
    private LocalDateTime createTime;

}