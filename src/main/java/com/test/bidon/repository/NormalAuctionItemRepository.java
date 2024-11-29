package com.test.bidon.repository;

import static com.test.bidon.entity.QNormalAuctionItem.normalAuctionItem;
import static com.test.bidon.entity.QNormalAuctionItemImage.normalAuctionItemImage;
import static com.test.bidon.entity.QNormalAuctionItemImageList.normalAuctionItemImageList;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.bidon.dto.NormalAuctionItem2DTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NormalAuctionItemRepository  {
	
	private final JPAQueryFactory jpaQueryFactory;
	
	 public List<NormalAuctionItem2DTO> NormalAuctionList() {
		 return jpaQueryFactory
				 .select(
						 normalAuctionItem.id,
						 normalAuctionItem.name,
						 normalAuctionItem.startPrice,
						 normalAuctionItemImage.path
					)
				 .from(normalAuctionItem)
				    .join(normalAuctionItemImageList)
				        .on(normalAuctionItemImageList.normalAuctionItem.id.eq(normalAuctionItem.id)) // 정상적으로 객체를 통해 연결
				    .join(normalAuctionItemImage)
				        .on(normalAuctionItemImage.id.eq(normalAuctionItemImageList.normalAuctionItemImage.id)) // 정상적으로 객체를 통해 연결
				        .where(normalAuctionItemImageList.isMainImage.eq(1)) // isMainImage가 true인 경우
				    .fetch()
				    .stream()
				    .map(record -> NormalAuctionItem2DTO.builder()
				        .auctionItemName(record.get(normalAuctionItem.name))
				        .path(record.get(normalAuctionItemImage.path)) // 필드명 맞추기
				        .startPrice(record.get(normalAuctionItem.startPrice)) // 필드명 맞추기
				        .auctionItemId(record.get(normalAuctionItem.id)) // 필드명 맞추기
				        .build())
				    .collect(Collectors.toList());
	    }
	}