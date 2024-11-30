package com.test.bidon.entity;

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
@Table(name = "WinningBid")
public class WinningBid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userInfoId")
    private Long userInfoId;

    @Column(name = "liveBidId")
    private Long liveBidId;

    @Column(name = "normalBidId")
    private Long normalBidId;


    // toString 메서드
    @Override
    public String toString() {
        return "WinningBid{" +
                "id=" + id +
                ", userInfoId=" + userInfoId +
                ", liveBidId=" + liveBidId +
                ", normalBidId=" + normalBidId +
                '}';
    }
}
