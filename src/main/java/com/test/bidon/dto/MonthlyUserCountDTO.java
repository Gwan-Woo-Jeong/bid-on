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
//@NoArgsConstructor
public class MonthlyUserCountDTO {
    private String month; // 예: "2024-11"
    private Long count; // 신규 가입자 수
    private Long cumulativeCount; // 누적 가입자 수

    // 기본 생성자
    public MonthlyUserCountDTO() {}

    // 매개변수를 받는 생성자
    public MonthlyUserCountDTO(String month, Long count) {
        this.month = month;
        this.count = count;
    }

    // 누적 가입자 수를 위한 생성자
    public MonthlyUserCountDTO(String month, Long count, Long cumulativeCount) {
        this.month = month;
        this.count = count;
        this.cumulativeCount = cumulativeCount;
    }

    // Getter와 Setter 메서드
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

    public void setCumulativeCount(Long cumulativeCount) {
        this.cumulativeCount = cumulativeCount;
    }
}