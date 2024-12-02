package com.test.bidon.dto;

import java.time.LocalDateTime;

import com.test.bidon.entity.NormalAuctionWish;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NormalAuctionWishDTO {

    private Long id;
    private Long userInfoId; // userInfoId만 필요함
    private Long normalAuctionItemId; // normalAuctionItemId만 필요함
    private String name;  // 경매 물품 이름
    private LocalDateTime startTime;  // 경매 시작 시간
    private long wishCount;  // wishCount 필드 추가

    // Projections.constructor()에 맞는 생성자 추가
    public NormalAuctionWishDTO(Long id, String name, LocalDateTime startTime, long wishCount) {
        this.id = id;
    	this.name = name;
        this.startTime = startTime;
        this.wishCount = wishCount;
    }

    // DTO -> Entity 변환
    public NormalAuctionWish toEntity() {
        return NormalAuctionWish.builder()
                .id(this.getId())
                .userInfoId(this.getUserInfoId())  // userInfoId 필드 사용
                .normalAuctionItemId(this.getNormalAuctionItemId())  // normalAuctionItemId 필드 사용
                .build();
    }
}
