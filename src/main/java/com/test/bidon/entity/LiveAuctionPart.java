package com.test.bidon.entity;

import com.test.bidon.dto.LiveAuctionDTO;
import com.test.bidon.dto.LiveAuctionPartDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@ToString
@Table(name = "LiveAuctionPart")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveAuctionPart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "liveAuctionPart_seq_generator")
    @SequenceGenerator(name = "liveAuctionPart_seq_generator", sequenceName = "seqLiveAuctionPart", allocationSize = 1)
    private Long id;

    @Column(nullable = false, insertable = false, updatable = false)
    private Long userInfoId;

    @Column(nullable = false, insertable = false, updatable = false)
    private Long liveAuctionItemId;
    
    @Column(nullable = false)
    private LocalDateTime createTime;

    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "userInfoId")
    private UserEntity userInfo;

    public LiveAuctionPartDTO toDTO() {
        return LiveAuctionPartDTO.builder()
                .id(this.getId())
                .userInfoId(this.getUserInfoId())
                .liveAuctionItemId(this.getLiveAuctionItemId())
                .createTime(this.getCreateTime())
                .userInfo(this.getUserInfo().toDTO())
                .build();
    }

}













