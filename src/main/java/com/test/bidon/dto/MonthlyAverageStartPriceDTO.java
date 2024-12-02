package com.test.bidon.dto;

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
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyAverageStartPriceDTO {
	private String month;
    private Double normalAuctionAmount;    // 일반경매 금액
    private Double liveAuctionAmount;      // 실시간경매 금액
    private Double totalAmount;            // 총 금액
    private Double growthRate;             // 증가율

}
