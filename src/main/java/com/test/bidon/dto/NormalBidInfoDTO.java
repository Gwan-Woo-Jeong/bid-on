package com.test.bidon.dto;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<String> bidderNames;
    private String bidderEmail;
    private String national;
    private Long normalAuctionItem;
    
    private List<String> imagePaths;
    private Integer startPrice;
    private String bidderName;
    
    private String sellerName;
    private String sellerEmail;
    private String sellerNational;
    private String sellerTel;

    @QueryProjection
    public NormalBidInfoDTO(Long id, Long auctionItemId, Long userInfoId, 
    						Integer bidPrice, LocalDateTime bidDate,
                            String auctionItemName, String auctionItemDescription, 
                            List<String> bidderNames, String bidderEmail, 
                            String national, Long normalAuctionItem, 
                            List<String> imagePaths, Integer startPrice,
                            String bidderName, String sellerName,
                            String sellerEmail, String sellerNational,
                            String sellerTel) {
        this.id = id;
        this.auctionItemId = auctionItemId;
        this.userInfoId = userInfoId;
        this.bidPrice = bidPrice;
        this.bidDate = bidDate;
        this.auctionItemName = auctionItemName;
        this.auctionItemDescription = auctionItemDescription;
        this.bidderNames = bidderNames;
        this.bidderEmail = bidderEmail;
        this.national = national;
        this.normalAuctionItem = normalAuctionItem;
        this.imagePaths = imagePaths;
        this.startPrice = startPrice;
        this.bidderName = bidderName;
        this.sellerName = sellerName;
        this.sellerEmail = sellerEmail;
        this.sellerNational = sellerNational;
        this.sellerTel = sellerTel; 
        
        
    }
}