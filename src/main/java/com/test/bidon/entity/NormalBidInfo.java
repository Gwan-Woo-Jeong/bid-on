package com.test.bidon.entity;

import java.time.LocalDateTime;

import com.test.bidon.dto.NormalBidInfoDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Table(name = "NormalBidInfo")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NormalBidInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "normalBidInfo_seq_generator")
    @SequenceGenerator(name = "normalBidInfo_seq_generator", sequenceName = "seqNormalBidInfo",allocationSize = 1)
    private Long id;

    private Long auctionItemId;
    private Long userInfoId;
    private Integer bidPrice;
    private LocalDateTime bidDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auctionItemId", referencedColumnName = "id", insertable = false, updatable = false)
    private NormalAuctionItem auctionItem;

    public static NormalBidInfoDTO toDTO(NormalBidInfo normalBidInfo) {
    	return NormalBidInfoDTO.builder()
    			.id(normalBidInfo.id)
    			.auctionItemId(normalBidInfo.auctionItemId)
    			.userInfoId(normalBidInfo.userInfoId)
    			.bidPrice(normalBidInfo.bidPrice)
    			.bidDate(normalBidInfo.bidDate)
    			.build();
    }
    
    // NormalBidInfo 엔티티를 DTO로 변환하는 메서드
    public NormalBidInfoDTO toDTO() {
        return NormalBidInfoDTO.builder()
                .id(this.id)
                .auctionItemId(this.auctionItemId)
                .userInfoId(this.userInfoId)
                .bidPrice(this.bidPrice)
                .bidDate(this.bidDate)
                .build();
    }
}
