package com.test.bidon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    private Long itemId;
    private String itemName;
    private String itemMainImagePath;
    private Integer deliveryDayCount;
    private LocalDateTime orderCreateDate;
    private String orderAddress;
    private String orderDetailAddress;
    private String orderCountry;
    private String orderCity;
    private String orderDistrict;

}