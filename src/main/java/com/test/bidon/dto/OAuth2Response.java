package com.test.bidon.dto;

//인증 사이트와 무관 > 공통 정보 제공 DTO
public interface OAuth2Response {

	//제공자(naver, google)
	String getProvider();
	
	//제공자에서 발급하는 아이디 or 번호
	String getProviderId();
	
	//이메일
	String getEmail();
	
	//사용자명
	String getName();
	
}
