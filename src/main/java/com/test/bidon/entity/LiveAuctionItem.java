package com.test.bidon.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.test.bidon.dto.LiveAuctionItemDTO;

import com.test.bidon.dto.UserInfoDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
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
    
    @Column(nullable = false)
    private Long userInfoId;
    
    @Transient
    private UserEntity userInfo;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer startPrice;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = true)
    private LocalDateTime endTime;
    

    @Transient  //Admin 페이지 실시간 경매 리스트 상태 표시 -민지
    private String status;

    @Column(nullable = false)
    private LocalDateTime createTime;
    
    @Column(nullable = true)
    private String brand;



    
    @OneToMany(mappedBy = "liveAuctionItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LiveAuctionItemImageList> imageLists;

    public LiveAuctionItemDTO toDTO() {
        UserEntity userInfo = this.getUserInfo(); // userInfo 가져오기

        UserInfoDTO userInfoDTO = null;
        if (userInfo != null) {
            userInfoDTO = userInfo.toDTO();
        }

        return LiveAuctionItemDTO.builder()
                .id(this.getId())
                .name(this.getName())
                .description(this.getDescription())
                .startPrice(this.getStartPrice())
                .startTime(this.getStartTime())
                .endTime(this.getEndTime())
                .createTime(this.getCreateTime())
                .userInfo(userInfoDTO)
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

    public void updateEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    @PrePersist
	public void prePersist() {
		this.createTime = this.createTime == null ? LocalDateTime.now() : this.createTime;
	}
    
    
    
 // **대표 이미지를 가져오는 메서드**
    @Transient
    public LiveAuctionItemImageList getMainImage() {
        return this.imageLists.stream()
                .filter(image -> image.getIsMainImage() == 1) // isMainImage가 1인 이미지 필터링
                .findFirst()
                .orElse(null); // 대표 이미지가 없으면 null 반환
    }

    // **대표 이미지를 설정하는 메서드**
    public void setMainImage(LiveAuctionItemImageList mainImage) {
        this.imageLists.forEach(image -> image.setIsMainImage(0)); // 모든 이미지를 일반 이미지로 초기화
        mainImage.setIsMainImage(1); // 선택된 이미지를 대표 이미지로 설정
    }
    
    
 // 필요 시 userInfoId를 가져오는 메서드
    public Long getUserInfoId() {
        return this.userInfo != null ? this.userInfo.getId() : null;
    }
    

}













