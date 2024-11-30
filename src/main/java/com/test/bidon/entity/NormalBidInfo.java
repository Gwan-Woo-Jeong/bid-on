package com.test.bidon.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NormalBidInfo")
public class NormalBidInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "auctionItemId")
    private Long auctionItemId;

    @Column(name = "UserInfoId")
    private Long userInfoId;

    @Column(name = "bidPrice")
    private Long bidPrice;

    @Column(name = "bidDate")
    private LocalDateTime bidDate;


    // toString 메서드
    @Override
    public String toString() {
        return "NormalBidInfo{" +
                "id=" + id +
                ", auctionItemId=" + auctionItemId +
                ", userInfoId=" + userInfoId +
                ", bidPrice=" + bidPrice +
                ", bidDate=" + bidDate +
                '}';
    }
}
