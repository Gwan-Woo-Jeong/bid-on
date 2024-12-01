package com.test.bidon.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CombinedAuctionDTO {
    private String auctionType;    // "실시간" 또는 "일반"
    private Long id;
    private String name;
    private Integer currentPrice;   // 현재 입찰가
    private Integer finalPrice;     // 낙찰가
    private Integer startPrice;     // 시작가
    private String remainingTime;   // 남은 시간
    private LocalDateTime soldDate; // 판매일/낙찰일
    private String buyerName;       // 구매자 이름
    private String sellerName;      // 판매자 이름 (낙찰한 경매에서 필요)
    private String status;          // 결제 상태 (낙찰한 경매에서 필요)
    private LocalDateTime bidDate;  // 입찰 날짜
    private LocalDateTime startTime; // 경매 시작 시간
    private LocalDateTime endTime;   // 경매 종료 시간
    private String sellStatus;      // "판매중" 또는 "판매완료"
}