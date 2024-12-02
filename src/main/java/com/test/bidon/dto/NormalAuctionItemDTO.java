package com.test.bidon.dto;

import java.time.LocalDateTime;

import com.test.bidon.entity.NormalAuctionItem;
import lombok.*;



@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NormalAuctionItemDTO {

    private Long id;

    private Long categorySubId;
    private Long userInfoId;
    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer startPrice;
    private String status;
    private String statusNormal;
    private Long wishCount;

	private UserInfoDTO userInfo;
	
	private Integer finalPrice;	//finalPrice는 OrderInfo에서 가져오는 값이므로 Entity 변환 시에는 포함시키지 않음 - HM -
	private String buyerName;  // 구매자 이름 필드 추가 - HM -
	private String remainingTime;	//경매 남은 시간 필드 추가 - HM - 
	
    // DTO 본인을 Entity로 변환하는 method
    public static NormalAuctionItem toEntity(NormalAuctionItemDTO dto) {

        return NormalAuctionItem.builder()
                .id(dto.getId())
                .categorySubId(dto.getCategorySubId())
                .userInfoId(dto.getUserInfoId())
                .name(dto.getName())
                .description(dto.getDescription())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .startPrice(dto.getStartPrice())
                .status(dto.getStatus())
                .build();
    }

    // Entity로부터 값을 받지 않고 Entity로 변환하는 method
    public NormalAuctionItem toEntity() {

        return NormalAuctionItem.builder()
                .id(this.getId())
                .categorySubId(this.getCategorySubId())
                .userInfoId(this.getUserInfoId())
                .name(this.getName())
                .description(this.getDescription())
                .startTime(this.getStartTime())
                .endTime(this.getEndTime())
                .startPrice(this.getStartPrice())
                .status(this.getStatus())
                .build();
    }
    
    
    public NormalAuctionItemDTO(Long id, String name, LocalDateTime startTime, String userProfile, String userName, String userEmail, long wishCount) {
        this.id = id;
    	this.name = name;
        this.startTime = startTime;
        this.wishCount = wishCount;
        this.userInfo = new UserInfoDTO(id, userProfile, userName, userEmail, userEmail, null, userEmail, userEmail, null, userEmail, startPrice, userEmail, null); 
    }
    
    
}
