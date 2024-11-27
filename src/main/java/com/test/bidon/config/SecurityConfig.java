package com.test.bidon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.test.bidon.config.handler.CustomAuthenticationFailureHandler;
import com.test.bidon.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
   
	@Autowired
	private final CustomOAuth2UserService customOAuth2UserService;
	
	@Autowired
	private final CustomAuthenticationFailureHandler authenticationFailureHandler;
   
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	   
       http.csrf(auth -> auth.disable());
       
       // 기본 인증 > 사용 안함 즉, 로컬에서 하는 모든 인증을 비활성화한다는 의미
       http.httpBasic(auth -> auth.disable());
       
       // 허가 URL
       http.authorizeHttpRequests(auth -> auth
               .requestMatchers("/").permitAll()
               .requestMatchers("/mypage").hasAnyRole("USER", "ADMIN")
               .requestMatchers("/js/**", "/css/**", "/images/**").permitAll()  // 정적 리소스 추가
               .anyRequest().permitAll()
               //.anyRequest().authenticated()  // 나머지 경로 > 인증 사용자만, 나중에 변경해야할 것
       );
       
       // 커스텀 로그인 설정
       http.formLogin(auth -> auth
    		   .loginPage("/login")
               .loginProcessingUrl("/loginok")
               .defaultSuccessUrl("/")
               .failureHandler(authenticationFailureHandler)  // 실패 핸들러 설정
               .permitAll()
       );
       
       // 세션 관리 설정 추가
       http.sessionManagement(session -> session
           .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // 필요할 때만 세션 생성
           .invalidSessionUrl("/login")  // 유효하지 않은 세션 리다이렉트
           .maximumSessions(1)  // 동시 세션 제한
           .expiredUrl("/login")  // 세션 만료시 리다이렉트
       );
       
       // 소셜 로그인 설정
       http.oauth2Login(auth -> auth
               .loginPage("/login")
               .defaultSuccessUrl("/")  // true 파라미터 제거
               .userInfoEndpoint(config -> config
                   .userService(customOAuth2UserService)
               )
           );
       
       // 로그아웃 설정을 여기로 통합
       http.logout(auth -> auth
               .logoutUrl("/logout")
               .logoutSuccessUrl("/")
               .invalidateHttpSession(true)  // 세션 무효화
               .clearAuthentication(true)    // 인증 정보 삭제
               .deleteCookies("JSESSIONID")  // 쿠키 삭제
       );
       
       return http.build();
   }
   

}