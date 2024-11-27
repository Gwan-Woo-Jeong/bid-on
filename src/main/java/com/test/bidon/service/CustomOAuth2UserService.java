package com.test.bidon.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.test.bidon.dto.CustomOAuth2User;
import com.test.bidon.dto.GoogleResponse;
import com.test.bidon.dto.OAuth2Response;
import com.test.bidon.dto.UserInfoDTO;
import com.test.bidon.entity.UserEntity;
import com.test.bidon.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public org.springframework.security.oauth2.core.user.OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("구글로부터 받아온 개인 정보 >>> " + oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // Google 로그인인 경우, GoogleResponse 객체로 변환, 구글 이외의 인증은 처리하지 않음
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        // 이메일과 이름 추출
        String email = oAuth2Response.getEmail();
        String name = oAuth2Response.getName();

        // DB에서 이메일로 사용자 조회
        UserEntity foundUser = userRepository.findByEmail(email);

        if (foundUser != null) {
            return new CustomOAuth2User(foundUser.toDTO());
        }

        System.out.println("우리 사이트에서 사용할 email: " + email);

        // 사용자 정보를 담을 DTO 생성
        UserInfoDTO newUserDTO = new UserInfoDTO();

        newUserDTO.setEmail(email);
        newUserDTO.setName(name);
        newUserDTO.setUserRole("ROLE_USER");    // 권한 설정

        UserEntity entity = UserEntity.builder()
                .email(email)
                .password("OAUTH2_GOOGLE")
                .name(name)
                .national(null)
                .birth(null)
                .tel(null)
                .profile(null)
                .createDate(java.time.LocalDate.now())
                .status(0)
                .userRole("ROLE_USER")
                .provider("google")
                .build();

        // 저장 전에 모든 필드값 출력
        System.out.println("=== 저장할 엔티티 정보 ===");
        System.out.println("email: " + entity.getEmail());
        System.out.println("password: " + entity.getPassword());
        System.out.println("name: " + entity.getName());
        System.out.println("national: " + entity.getNational());
        System.out.println("birth: " + entity.getBirth());
        System.out.println("tel: " + entity.getTel());
        System.out.println("profile: " + entity.getProfile());
        System.out.println("createDate: " + entity.getCreateDate());
        System.out.println("status: " + entity.getStatus());
        System.out.println("userRole: " + entity.getUserRole());
        System.out.println("provider: " + entity.getProvider());
        System.out.println("=========================");

        try {
            userRepository.save(entity);
            System.out.println("새로운 사용자 저장 성공: " + email);
        } catch (Exception e) {
            System.out.println("사용자 저장 실패: " + e.getMessage());
            e.printStackTrace();
        }

        return new CustomOAuth2User(newUserDTO);
    }
}
