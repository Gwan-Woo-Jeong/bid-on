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
//@AllArgsConstructor
@NoArgsConstructor
public class MonthlyUserCountDTO {
	private String month;
    private Long count;
    private Long cumulativeCount;
    
    public MonthlyUserCountDTO(String month, Long count) {
        this.month = month;
        this.count = count;
    }
    
 // 생성자
    public MonthlyUserCountDTO(String month, Long count, Long cumulativeCount) {
        this.month = month;
        this.count = count;
        this.cumulativeCount = (long) 0;
    }
    
    // Getters and Setters
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getCumulativeCount() {
        return cumulativeCount;
    }

    public void setCumulativeCount(long cumulativeCount) {
        this.cumulativeCount = cumulativeCount;
    }

	
}
