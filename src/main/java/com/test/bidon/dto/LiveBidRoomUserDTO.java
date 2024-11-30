package com.test.bidon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveBidRoomUserDTO {

    private Long partId;
    private Long userId;
    private String email;
    private String name;
    private String profile;
    private String national;
    private String tel;
    private Integer bidPrice;

}
