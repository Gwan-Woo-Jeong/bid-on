
package com.test.bidon.config;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

//1. @Configuration 어노테이션 추가
@Configuration
@RequiredArgsConstructor // 3. @RequiredArgsConstructor 어노테이션 추가 
public class QueryDSLConfig { // = root-context.xml 같은 설정 파일 역할
	
	//2. <bean>추가하기 위한 객체 생성
	private final EntityManager em;   //JPA에서 SQL을 실행하는 객체 > Statement 객체, SqlSessionTemplate 객체 역할

	
	//4. <bean class="JPAQueryFactory">
	@Bean
	public JPAQueryFactory jpaQueryFactory() {
		return new JPAQueryFactory(em);
	}


	
	
}
