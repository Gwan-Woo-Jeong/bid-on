package com.test.bidon.entity;

import com.test.bidon.dto.NormalAuctionItemDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Table(name="NormalAuctionItem")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NormalAuctionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NormalAuctionItem_seq_generator")
    @SequenceGenerator(name = "NormalAuctionItem_seq_generator", sequenceName = "id", allocationSize = 1)
    private Long id;

    private Long categorySubId;
    private Long userInfoId;
    private String name;
    private String description;
    private String startTime;
    private String endTime;
    private String startPrice;
    private String status;

    // Entity 본인을 DTO로 변환시키는 method
    public static NormalAuctionItemDTO toDTO(NormalAuctionItem item) {

        return NormalAuctionItemDTO.builder()
                .id(item.id)
                .categorySubId(item.categorySubId)
                .name(item.name)
                .description(item.description)
                .startTime(item.startTime)
                .endTime(item.endTime)
                .startPrice(item.startPrice)
                .status(item.status)
                .build();
    }

    // DTO로부터 값을 받지 않고 스스로 DTO로 변환되는 method
    public NormalAuctionItemDTO toDTO() {

        return NormalAuctionItemDTO.builder()
                .id(this.id)
                .categorySubId(this.categorySubId)
                .name(this.name)
                .description(this.description)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .startPrice(this.startPrice)
                .status(this.status)
                .build();
    }

    // Setter. 물품 정보 수정을 대비해 생성
    public void updateNormalAuctionItem(String name, String startPrice) {
        this.name = name;
        this.startPrice = startPrice;
    }
}
