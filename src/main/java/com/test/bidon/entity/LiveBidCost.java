package com.test.bidon.entity;

import java.time.LocalDateTime;

import com.test.bidon.dto.LiveBidCostDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Table(name = "LiveBidCost")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveBidCost {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "liveBidCost_seq_generator")
    @SequenceGenerator(name = "liveBidCost_seq_generator", sequenceName = "seqLiveBidCost", allocationSize = 1)
    private Long id;

    @Column(nullable = false, insertable = false, updatable = false)
    private Long liveAuctionPartId;

    @Column(nullable = false, insertable = false, updatable = false)
    private Long liveAuctionItemId;

    @Column(nullable = false)
    private Integer bidPrice;
    
    @Column(nullable = false)
    private LocalDateTime bidTime;

    @ManyToOne
    @JoinColumn(name = "liveAuctionPartId")
    private LiveAuctionPart liveAuctionPart;

    @ManyToOne
    @JoinColumn(name = "liveAuctionItemId")
    private LiveAuctionItem liveAuctionItem;

    public LiveBidCostDTO toDTO() {
        return LiveBidCostDTO.builder()
                .id(this.getId())
                .liveAuctionPartId(this.getLiveAuctionPartId())
                .liveAuctionItemId(this.getLiveAuctionItemId())
                .bidPrice(this.getBidPrice())
                .liveAuctionPart(this.getLiveAuctionPart().toDTO())
                .liveAuctionItem(this.getLiveAuctionItem().toDTO())
                .build();
    }

}
