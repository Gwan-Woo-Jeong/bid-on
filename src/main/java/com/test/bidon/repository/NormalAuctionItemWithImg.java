package com.test.bidon.repository;

import static com.test.bidon.entity.QNormalAuctionItem.normalAuctionItem;  // QNormalAuctionItem 임포트
import static com.test.bidon.entity.QNormalAuctionItemImage.normalAuctionItemImage;
import static com.test.bidon.entity.QNormalAuctionItemImageList.normalAuctionItemImageList;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.bidon.dto.NormalAuctionItemWithImgDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NormalAuctionItemWithImg {

	private final JPAQueryFactory jpaQueryFactory;
	
	public List<NormalAuctionItemWithImgDTO> findItemImg() {
		List<Tuple> results = jpaQueryFactory
				.select(
						normalAuctionItem.id, 
						normalAuctionItem.name, 
						normalAuctionItemImage.path
						)
				.from(normalAuctionItemImageList)
                .join(normalAuctionItemImageList.normalAuctionItem, normalAuctionItem)
                .join(normalAuctionItemImageList.normalAuctionItemImage, normalAuctionItemImage)
                .fetch();
        return results.stream()
                .collect(Collectors.groupingBy(
                    tuple -> tuple.get(0, Long.class),  // 아이템 ID를 기준으로 그룹화
                    Collectors.mapping(
                        tuple -> tuple.get(2, String.class),  // 이미지 URL을 리스트로 수집
                        Collectors.toList()
                    )
                ))
                .entrySet().stream()  // 그룹화된 결과를 스트림으로 변환
                .map(entry -> new NormalAuctionItemWithImgDTO(entry.getKey(), "아이템 이름", entry.getValue()))
                .collect(Collectors.toList());
        }
}
