package com.test.bidon.entity;

import java.time.LocalDateTime;

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
    
    @Column(nullable = false, insertable = false, updatable = false)
    private Long userInfoId;
    
    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer startPrice;
    private String status;
    
    @Transient
    private String statusNormal;
    
    @ManyToOne
    @JoinColumn(name = "userInfoId")
    private UserEntity userInfo;
  

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
    public void updateNormalAuctionItem(String name, Integer startPrice) {
        this.name = name;
        this.startPrice = startPrice;
    }
    
 // 상태를 계산하는 메서드
    public void calculateStatus(LocalDateTime currentTime) {
        if (this.startTime.isAfter(currentTime)) {
            this.statusNormal = "경매대기";  // 경매 대기 상태
        } else if (this.endTime != null && this.endTime.isBefore(currentTime)) {
            this.statusNormal = "경매종료";  // 경매 종료 상태
        } else {
            this.statusNormal = "경매진행";  // 경매 진행 중 상태
        }
    }
    
 
    
    
}
