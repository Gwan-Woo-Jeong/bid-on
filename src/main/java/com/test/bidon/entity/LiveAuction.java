package com.test.bidon.entity;

import com.test.bidon.dto.LiveAuctionDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@ToString
@Table(name = "LiveAuction")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveAuction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "liveAuction_seq_generator")
    @SequenceGenerator(name = "liveAuction_seq_generator", sequenceName = "seqLiveAuction", allocationSize = 1)
    private Long id;

    @Column(nullable = false, insertable = false, updatable = false)
    private Long liveAuctionItemId;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "liveAuctionItemId", referencedColumnName = "id")
    private LiveAuctionItem liveAuctionItem;

    public LiveAuctionDTO toDTO() {
        return LiveAuctionDTO.builder()
                .id(this.getId())
                .liveAuctionItemId(this.getLiveAuctionItemId())
                .liveAuctionItem(this.getLiveAuctionItem().toDTO())
                .build();
    }
    
    

}













