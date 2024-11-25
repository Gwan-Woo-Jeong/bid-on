package com.test.bidon.dto;

import java.util.Map;

public class GoogleResponse implements OAuth2Response {

	private final Map<String, Object> attribute;
	
	public GoogleResponse(Map<String, Object> attribute) {
		this.attribute = attribute;	//여기 수정
	}

	@Override
	public String getProvider() {

		return "google";
	}

	@Override
	public String getProviderId() {

		//return attribute.get("sub").toString();
		return attribute.get("given_name").toString();
	}

	@Override
	public String getEmail() {

		return attribute.get("email").toString();
	}

	@Override
	public String getName() {

		return attribute.get("name").toString();
	}
	
}
