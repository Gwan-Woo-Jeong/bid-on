package com.test.bidon.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "OrderInfo")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqorderinfo")
    @SequenceGenerator(name = "seqorderinfo", sequenceName = "seqOrderInfo", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private Long winningBidId;

    @Column(nullable = false)
    private Long deliveryMethodId;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 350)
    private String email;

    @Column(nullable = false, length = 20)
    private String tel;

    @Column(nullable = false, length = 300)
    private String address;

    @Column(nullable = false, length = 300)
    private String detailAddress;

    @Column(nullable = false, length = 50)
    private String zipcode;

    @Column(nullable = false, length = 100)
    private String district;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(nullable = false)
    private Integer finalPrice;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @OneToOne(mappedBy = "orderInfo")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winningBidId", insertable = false, updatable = false)
    private WinningBid winningBid;
    
}
