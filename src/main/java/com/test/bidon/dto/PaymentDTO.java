package com.test.bidon.dto;

import com.test.bidon.entity.Payment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    private Long id;
    private Long orderId;
    private LocalDateTime createDate;
    private String status;
    private String method;
    private Integer paymentPrice;
    private Integer depositPrice;
    private String depositBank;
    private String depositName;

    public Payment toEntity() {
        return Payment.builder()
                .id(id)
                .orderId(orderId)
                .createDate(createDate)
                .status(status)
                .method(method)
                .paymentPrice(paymentPrice)
                .depositPrice(depositPrice)
                .depositBank(depositBank)
                .depositName(depositName)
                .build();
    }
}