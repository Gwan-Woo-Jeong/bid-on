package com.test.bidon.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
public class NormalBidInfoDTO {

    private Long id;
    private Long auctionItemId; 
    private Long userInfoId; 
    private Integer bidPrice;
    private LocalDateTime bidDate;

    private String auctionItemName;
    private String auctionItemDescription;
    private String bidderName;
    private String bidderEmail;
    private String national;
    private Long normalAuctionItem;

    @QueryProjection
    public NormalBidInfoDTO(Long id, Long auctionItemId, Long userInfoId, Integer bidPrice, LocalDateTime bidDate, 
                            String auctionItemName, String auctionItemDescription, String bidderName, 
                            String bidderEmail, String national, Long normalAuctionItem) {
        this.id = id;
        this.auctionItemId = auctionItemId;
        this.userInfoId = userInfoId;
        this.bidPrice = bidPrice;
        this.bidDate = bidDate;
        this.auctionItemName = auctionItemName;
        this.auctionItemDescription = auctionItemDescription;
        this.bidderName = bidderName;
        this.bidderEmail = bidderEmail;
        this.national = national;
        this.normalAuctionItem = normalAuctionItem;
    }
}
