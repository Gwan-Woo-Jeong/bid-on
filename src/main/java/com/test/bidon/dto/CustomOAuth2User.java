package com.test.bidon.dto;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {

	private final UserInfoDTO userDTO;
	
	public CustomOAuth2User(UserInfoDTO userDTO) {
		this.userDTO = userDTO;
	}
	
	@Override
    public Map<String, Object> getAttributes() {
        //필요한 속성들을 Map에 담아서 반환
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("id", userDTO.getId().toString());
        attributes.put("provider", userDTO.getProvider());
        attributes.put("status", userDTO.getStatus());
        attributes.put("createDate", userDTO.getCreateDate());
        attributes.put("tel", userDTO.getTel());
        attributes.put("profile", userDTO.getProfile());
        attributes.put("birth", userDTO.getBirth());
        attributes.put("national", userDTO.getNational());
        attributes.put("userRole", userDTO.getUserRole());
        attributes.put("email", userDTO.getEmail());
        attributes.put("name", userDTO.getName());

        return attributes;
    }

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // SimpleGrantedAuthority 사용하여 권한 생성
        return Collections.singleton(new SimpleGrantedAuthority(userDTO.getUserRole()));
    }

    @Override
    public String getName() {
        return userDTO.getName();
    }

    // 추가 정보 메서드
    public String getEmail() {
        return userDTO.getEmail();
    }
    
    public String getRole() {
        return userDTO.getUserRole();
    }
}
