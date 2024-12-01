package com.test.bidon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyRevenueDTO {
	private String month;
    private long totalRevenue;
    private long cumulativeTotalRevenue;
}
