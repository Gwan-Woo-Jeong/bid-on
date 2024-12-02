package com.test.bidon.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class NormalUserFindDTO {
    
    private Long id;
    private Long auctionItemId;
    private Long userInfoId;
    private Integer bidPrice;
    private LocalDateTime bidDate;
    
    private String auctionItemName;
    private String auctionItemDescription;
    private Integer startPrice;
    private Long normalAuctionItem;
    
    private String name;
    private String national;
    
    
    // 생성자 (매개변수를 갖는 생성자)
    public NormalUserFindDTO(Long id, Long auctionItemId, Long userInfoId, 
                             Integer bidPrice, LocalDateTime bidDate,
                             String auctionItemName, String auctionItemDescription, 
                             Long normalAuctionItem, Integer startPrice,
                             String name, String national) {
        this.id = id;
        this.auctionItemId = auctionItemId;
        this.userInfoId = userInfoId;
        this.bidPrice = bidPrice;
        this.bidDate = bidDate;
        this.auctionItemDescription = auctionItemDescription;
        this.auctionItemName = auctionItemName;
        this.startPrice = startPrice;
        this.normalAuctionItem = normalAuctionItem;
        this.name = name;
        this.national = national;
    }
}
