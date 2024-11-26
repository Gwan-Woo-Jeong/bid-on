package com.test.bidon.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

// root-context.xml 같은 설정 파일 역할을 하는 클래스 파일. Query DSL을 사용할 수 있게 함
@Configuration
@RequiredArgsConstructor
public class QueryDSLConfig {

    // JPA에서 SQL을 실행하는 객체.
    private final EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }

    @Bean
    public Random random() {
        return new Random();
    }
}
