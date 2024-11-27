package com.test.bidon.dto;

import com.test.bidon.entity.NormalAuctionItem;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NormalAuctionItemDTO {

    private Long seq;

    private Long categorySubId;
    private Long userInfoId;
    private String name;
    private String description;
    private String startTime;
    private String endTime;
    private String startPrice;
    private String status;

    // DTO 본인을 Entity로 변환하는 method
    public static NormalAuctionItem toEntity(NormalAuctionItemDTO dto) {

        return NormalAuctionItem.builder()
                .seq(dto.getSeq())
                .categorySubId(dto.getCategorySubId())
                .userInfoId(dto.getUserInfoId())
                .name(dto.getName())
                .description(dto.getDescription())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .startPrice(dto.getStartPrice())
                .status(dto.getStatus())
                .build();
    }

    // Entity로부터 값을 받지 않고 Entity로 변환하는 method
    public NormalAuctionItem toEntity() {

        return NormalAuctionItem.builder()
                .seq(this.getSeq())
                .categorySubId(this.getCategorySubId())
                .userInfoId(this.getUserInfoId())
                .name(this.getName())
                .description(this.getDescription())
                .startTime(this.getStartTime())
                .endTime(this.getEndTime())
                .startPrice(this.getStartPrice())
                .status(this.getStatus())
                .build();
    }
}
