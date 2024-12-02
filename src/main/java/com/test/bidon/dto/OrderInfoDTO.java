package com.test.bidon.dto;

import com.test.bidon.entity.OrderInfo;
import com.test.bidon.entity.WinningBid;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoDTO {

    private Long id;
    private Long winningBidId;
    private Long deliveryMethodId;
    private String lastName;
    private String firstName;
    private String email;
    private String tel;
    private String address;
    private String detailAddress;
    private String zipcode;
    private String district;
    private String city;
    private String country;
    private Integer finalPrice;
    private LocalDateTime createDate;

    private WinningBid winningBid;

    public OrderInfo toEntity(){
        return OrderInfo.builder()
                .id(id)
                .winningBidId(winningBidId)
                .winningBid(winningBid)
                .deliveryMethodId(deliveryMethodId)
                .lastName(lastName)
                .firstName(firstName)
                .email(email)
                .tel(tel)
                .address(address)
                .detailAddress(detailAddress)
                .zipcode(zipcode)
                .district(district)
                .city(city)
                .country(country)
                .finalPrice(finalPrice)
                .createDate(createDate)
                .build();
    }

}