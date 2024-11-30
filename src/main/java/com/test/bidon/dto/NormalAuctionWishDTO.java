package com.test.bidon.dto;

import com.test.bidon.entity.NormalAuctionWish;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    // 필요없으면 제거하거나, 엔티티 변환 시에만 사용
    private UserInfoDTO userInfo;
    private NormalAuctionItemDTO normalAuctionItem;

    // DTO -> Entity 변환
    public NormalAuctionWish toEntity() {
        return NormalAuctionWish.builder()
                .id(this.getId())
                .userInfoId(this.getUserInfoId())  // userInfoId 필드 사용
                .normalAuctionItemId(this.getNormalAuctionItemId())  // normalAuctionItemId 필드 사용
                .build();
    }


}
