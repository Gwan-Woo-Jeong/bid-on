package com.test.bidon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyItemCountDTO {
    private String month;
    private long registeredCount;
    private long ongoingCount;
    private long endCount;
    
  @Override
  public String toString() {
      return "MonthlyItemCountDTO{month=" + month + 
    		  ", registeredCount=" + registeredCount + 
    		  ", ongoingCount=" + ongoingCount +  
    		  ", endCount=" + endCount + "}";
  }

  public MonthlyItemCountDTO(String month, Long registeredCount, Long ongoingCount, Long endCount) {
      this.month = month;
      this.registeredCount = registeredCount != null ? registeredCount : 0L;
      this.ongoingCount = ongoingCount != null ? ongoingCount : 0L;
      this.endCount = endCount != null ? endCount : 0L;
  }

}
