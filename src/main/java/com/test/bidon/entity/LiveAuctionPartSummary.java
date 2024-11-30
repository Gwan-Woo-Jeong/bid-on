package com.test.bidon.entity;

import com.test.bidon.dto.LiveAuctionPartDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Table(name = "LiveAuctionPart")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveAuctionPartSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "liveAuctionPart_seq_generator")
    @SequenceGenerator(name = "liveAuctionPart_seq_generator", sequenceName = "seqLiveAuctionPart", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private Long userInfoId;

    @Column(nullable = false)
    private Long liveAuctionItemId;

    @Column(nullable = false)
    private LocalDateTime createTime;

    public void updateUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public void updateLiveAuctionItemId(Long liveAuctionItemId) {
        this.liveAuctionItemId = liveAuctionItemId;
    }

    public void updateCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

}













