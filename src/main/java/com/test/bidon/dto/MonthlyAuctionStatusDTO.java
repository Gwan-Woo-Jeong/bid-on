package com.test.bidon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyAuctionStatusDTO {
		private String month;
		private Long normalRegistered;
	    private Long liveRegistered;
	    private Long normalInProgress;
	    private Long liveInProgress;
	    private Long normalCompleted;
	    private Long liveCompleted;

}
