package com.test.bidon.entity;

import com.test.bidon.dto.LiveAuctionItemDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@ToString
@Table(name = "LiveAuctionItem")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveAuctionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "liveAuctionItem_seq_generator")
    @SequenceGenerator(name = "liveAuctionItem_seq_generator", sequenceName = "seqLiveAuctionItem", allocationSize = 1)
    private Long id;

    @Column(nullable = false, insertable = false, updatable = false)
    private Long userInfoId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer startPrice;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;
    
    @Column(nullable = false)
    private LocalDateTime createTime;
    
    @Column(nullable = true)
    private String brand;

    @ManyToOne
    @JoinColumn(name = "userInfoId")
    private UserEntity userInfo;

    public LiveAuctionItemDTO toDTO() {
        return LiveAuctionItemDTO.builder()
                .id(this.getId())
                .userInfoId(this.getUserInfoId())
                .name(this.getName())
                .description(this.getDescription())
                .startPrice(this.getStartPrice())
                .startTime(this.getStartTime())
                .endTime(this.getEndTime())
                .createTime(this.getCreateTime())
                .userInfo(this.getUserInfo().toDTO())
                .build();
    }

}













