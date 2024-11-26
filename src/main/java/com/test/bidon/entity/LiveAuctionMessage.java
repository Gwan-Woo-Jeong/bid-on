package com.test.bidon.entity;

import com.test.bidon.dto.LiveAuctionMessageDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Table(name = "LiveAuctionMessage")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveAuctionMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "liveAuctionMessage_seq_generator")
    @SequenceGenerator(name = "liveAuctionMessage_seq_generator", sequenceName = "seqLiveAuctionMessage", allocationSize = 1)
    private Long id;

    @Column(nullable = false, insertable = false, updatable = false)
    private Long liveAuctionPartId;

    @ManyToOne
    @JoinColumn(name = "liveAuctionPartId")
    private LiveAuctionPart liveAuctionPart;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String content;

    public LiveAuctionMessageDTO toDTO() {
        return LiveAuctionMessageDTO.builder()
                .id(this.getId())
                .liveAuctionPartId(this.getLiveAuctionPartId())
                .type(this.getType())
                .content(this.getContent())
                .liveAuctionPart(this.getLiveAuctionPart().toDTO())
                .build();

    }
}
