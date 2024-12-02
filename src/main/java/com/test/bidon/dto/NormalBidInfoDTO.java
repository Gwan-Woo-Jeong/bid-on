package com.test.bidon.dto;

import java.time.LocalDate;
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
    private Long normalAuctionItem;
    private List<String> bidderNames;
    
    private List<String> imagePaths;
    private Integer startPrice;
    
    private String name;
    private String email;
    private String national;
    private String tel;
    private LocalDate createDate;
    

    @QueryProjection
    public NormalBidInfoDTO(Long id, Long auctionItemId, Long userInfoId, 
    						Integer bidPrice, LocalDateTime bidDate,
                            String auctionItemName, String auctionItemDescription,
                            Long normalAuctionItem, List<String> bidderNames, 
                            List<String> imagePaths, 
                            Integer startPrice,  String name,
                            String email,  String national,
                            String tel, LocalDate createDate) {
     
        this.id = id;
        this.auctionItemId = auctionItemId;
        this.userInfoId = userInfoId;
        this.bidPrice = bidPrice;
        this.bidDate = bidDate;
        this.auctionItemName = auctionItemName;
        this.auctionItemDescription = auctionItemDescription;
        this.normalAuctionItem = normalAuctionItem;
        this.bidderNames = bidderNames;
        this.imagePaths = imagePaths;
        this.startPrice = startPrice;
        
        this.name = name;
        this.email = email;
        this.national = national;
        this.tel = tel;
        this.createDate = createDate;
        
        
    }
}