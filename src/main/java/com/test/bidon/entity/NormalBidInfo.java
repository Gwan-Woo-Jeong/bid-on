package com.test.bidon.entity;

import com.test.bidon.dto.NormalBidInfoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Table(name = "NormalBidInfo")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NormalBidInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "normalBidInfo_seq_generator")
    @SequenceGenerator(name = "normalBidInfo_seq_generator", sequenceName = "seqNormalBidInfo", allocationSize = 1)
    private Long id;

    @Column(insertable = false, updatable = false)
    private Long auctionItemId;

    @Column(insertable = false, updatable = false)
    private Long userInfoId;

    private Integer bidPrice;
    private LocalDateTime bidDate;

    // Entity 본인을 DTO로 변환시키는 method
    public static NormalBidInfoDTO normalBidInfoDTO(NormalBidInfo info) {

        return NormalBidInfoDTO.builder()
                .id(info.id)
                .auctionItemId(info.auctionItemId)
                .userInfoId(info.userInfoId)
                .bidPrice(info.bidPrice)
                .bidDate(info.bidDate)
                .build();
    }

    // DTO로부터 값을 받지 않고 본인 스스로를 DTO로 변환시키는 method
    public NormalBidInfoDTO normalBidInfoDTO() {

        return NormalBidInfoDTO.builder()
                .id(this.id)
                .auctionItemId(this.auctionItemId)
                .userInfoId(this.userInfoId)
                .bidPrice(this.bidPrice)
                .bidDate(this.bidDate)
                .build();
    }

    // 자식(NormalBidInfo) -> 부모(NormalAuctionItem) 참조
    @ManyToOne
    @JoinColumn(name = "auctionItemId")
    private NormalAuctionItem normalAuctionItem;
}
