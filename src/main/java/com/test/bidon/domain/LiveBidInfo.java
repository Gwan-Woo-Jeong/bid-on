package com.test.bidon.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LiveBidInfo {

    private String status;
    private LiveBidRoomUser highestBidder;
    private Integer highestBidPrice;

}
