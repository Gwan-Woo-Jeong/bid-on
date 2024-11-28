package com.test.bidon.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Message {

	private String code;
	private String userId;
	private String content;
	private String regdate;
	
}
