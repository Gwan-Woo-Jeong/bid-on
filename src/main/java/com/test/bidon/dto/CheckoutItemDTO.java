package com.test.bidon.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutItemDTO {

    private Long itemId;
    private String itemName;
    private String itemMainImagePath;
    private Integer startPrice;
    private Integer bidPrice;
    private LocalDateTime createTime;
    private Long sellerId;
    private String sellerName;
    private String sellerEmail;
    private LocalDate sellerJoinDate;
    private String sellerNational;
    private String sellerTel;

}