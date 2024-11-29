package com.test.bidon.entity;

import com.test.bidon.dto.NormalAuctionItemImageListDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Table(name="NormalAuctionItemImageList")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NormalAuctionItemImageList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NormalAuctionItemImageList_seq_generator")
    @SequenceGenerator(name = "NormalAuctionItemImageList_seq_generator", sequenceName = "id", allocationSize = 1)
    private Long id;

    // NormalAuctionItemImage의 PK이자 NormalAuctionItemImageList의 FK
    @Column(insertable = false, updatable = false)
    private Long normalAuctionItemImageId;

    // NormalAuctionItem의 PK이자 NormalAuctionItemImageList의 FK
    @Column(insertable = false, updatable = false)
    private Long normalAuctionItemId;

    private Long isMainImage;


    // Entity 본인을 DTO로 변환시키는 method
    public static NormalAuctionItemImageListDTO toDTO(NormalAuctionItemImageList list) {

        return NormalAuctionItemImageListDTO.builder()
                .id(list.id)
                .normalAuctionItemImageId(list.normalAuctionItemImageId)
                .normalAuctionItemId(list.normalAuctionItemId)
                .build();

    }

    // DTO로부터 값을 받지 않고 스스로 DTO로 변환되는 method
    public NormalAuctionItemImageListDTO toDTO() {

        return NormalAuctionItemImageListDTO.builder()
                .id(this.id)
                .normalAuctionItemImageId(this.normalAuctionItemImageId)
                .normalAuctionItemId(this.normalAuctionItemId)
                .build();
    }

    // 자식(NormalAuctionItemImageList) -> 부모(NormalAuctionItem) 참조
    @ManyToOne
    @JoinColumn(name = "normalAuctionItemId")
    private NormalAuctionItem normalAuctionItem;

    // 자식(NormalAuctionItemImageList) -> 부모(NormalAuctionItemImage) 참조
    @OneToOne
    @JoinColumn(name = "normalAuctionItemImageId")
    private NormalAuctionItemImage normalAuctionItemImage;
}
