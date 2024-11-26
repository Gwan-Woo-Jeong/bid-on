package com.test.bidon.repository;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

//Query DSL용 Repository
@Repository
@RequiredArgsConstructor
public class CustomNormalAuctionItemRepository {

    private final JPAQueryFactory jpaQueryFactory;

}
