package com.test.bidon.entity;

import com.test.bidon.dto.LiveAuctionItemImageListDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Table(name = "LiveAuctionItemImageList")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveAuctionItemImageList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "liveAuctionItemImageList_seq_generator")
    @SequenceGenerator(name = "liveAuctionItemImageList_seq_generator", sequenceName = "seqLiveAuctionItemImageList", allocationSize = 1)
    private Long id;

    @Column(nullable = false, insertable = false, updatable = false)
    private Long liveAuctionItemImageId;

    @Column(nullable = false, insertable = false, updatable = false)
    private Long liveAuctionItemId;

    @ManyToOne
    @JoinColumn(name = "liveAuctionItemImageId")
    private LiveAuctionItemImage liveAuctionItemImage;

    @ManyToOne
    @JoinColumn(name = "liveAuctionItemId")
    private LiveAuctionItem liveAuctionItem;

    @Column(nullable = false)
    private Integer isMainImage;

    public LiveAuctionItemImageListDTO toDTO() {
        return LiveAuctionItemImageListDTO.builder()
                .id(this.getId())
                .liveAuctionItemImageId(this.getLiveAuctionItemImageId())
                .liveAuctionItemId(this.getLiveAuctionItemId())
                .isMainImage(this.getIsMainImage())
                .liveAuctionItemImage(this.getLiveAuctionItemImage().toDTO())
                .liveAuctionItem(this.getLiveAuctionItem().toDTO())
                .build();
    }

}













