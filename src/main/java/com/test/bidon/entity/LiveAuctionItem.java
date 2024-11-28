package com.test.bidon.entity;

import com.test.bidon.dto.LiveAuctionItemDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


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
    
    @Transient  //Admin 페이지 실시간 경매 리스트 상태 표시 -민지
    private String status;

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
                .userInfo(this.getUserInfo().toDTO())
                .build();
    }
    
 // 상태를 계산하는 메서드
    public void calculateStatus(LocalDateTime currentTime) {
        if (this.startTime.isAfter(currentTime)) {
            this.status = "경매대기";  // 경매 대기 상태
        } else if (this.endTime != null && this.endTime.isBefore(currentTime)) {
            this.status = "경매종료";  // 경매 종료 상태
        } else {
            this.status = "경매진행";  // 경매 진행 중 상태
        }
    }
    
    

}













