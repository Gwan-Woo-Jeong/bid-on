package com.test.bidon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.test.bidon.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    	http.csrf(auth -> auth.disable());
    	
    	//Form Login > 사용 안함 > 소셜 인증
    	http.formLogin(auth -> auth.disable());
    			
    	//기본 인증 > 사용 안함 즉, 로컬에서 하는 모든 인증을 비활성화한다는 의미
    	http.httpBasic(auth -> auth.disable());
    	
    	//허가 URL
        http.authorizeHttpRequests(auth -> auth
        		.requestMatchers("/", "/login/**", "/oauth2/**", "/join", "/joinok").permitAll()
        		.requestMatchers("/my").hasAnyRole("MEMBER", "ADMIN")
        		.requestMatchers("/js/**", "/css/**", "/images/**").permitAll()  //정적 리소스 추가
                .anyRequest().permitAll()
                //.anyRequest().authenticated()	//나머지 경로 > 인증 사용자만, 나중에 변경해야할 것
        );
        
        //커스텀 로그인 설정
      	http.formLogin(auth -> auth
      					.loginPage("/login")	//사용자 로그인 페이지 URL
      					.defaultSuccessUrl("/")
      					.loginProcessingUrl("/loginok").permitAll()
      	);
        
        //소셜 로그인 설정
        http.oauth2Login(auth -> auth
      					.loginPage("/login")
      					.userInfoEndpoint(config -> config.userService(customOAuth2UserService))
        );
    
        
        //로그아웃 설정을 여기로 통합
        http.logout(auth -> auth
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true) // 세션 무효화
            .clearAuthentication(true)   // 인증 정보 삭제
            .deleteCookies("JSESSIONID") // 쿠키 삭제
        );
        

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
    

}










