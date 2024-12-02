package com.test.bidon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuarterlyRevenueDTO {
	
	private int quarter;
	private double normalRevenue;
	private double liveRevenue;

}
