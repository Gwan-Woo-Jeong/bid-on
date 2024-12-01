package com.test.bidon.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "Payment")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqpayment")
    @SequenceGenerator(name = "seqpayment", sequenceName = "seqPayment", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false, length = 10)
    private String status;

    @Column(name = "method", nullable = false, length = 30)
    private String method;

    @Column(nullable = false)
    private Integer paymentPrice;

    private Integer depositPrice;

    @Column(nullable = false, length = 100)
    private String depositBank;

    @Column(nullable = false, length = 30)
    private String depositName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", insertable = false, updatable = false)
    private OrderInfo orderInfo;
}
