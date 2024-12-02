package com.test.bidon.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.bidon.entity.LiveAuctionItem;
import com.test.bidon.entity.LiveAuctionItemImage;
import com.test.bidon.service.LiveAuctionRegisterService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/auction")
public class AuctionRegistrationController {
	
		private static final Logger logger = LoggerFactory.getLogger(AuctionRegistrationController.class);
	    private final LiveAuctionRegisterService liveAuctionRegisterService;

	    @GetMapping("/bid-registration")
	    public String showAuctionRegistration(Model model) {
	        return "user/bid-registration";
	    }

	    @PostMapping("/auctionRegistration")
	    public String auctionRegistration(
	            @RequestParam("userInfoId") Long userInfoId,
	            @RequestParam("auctionType") String auctionType,
	            @RequestParam(value = "category", required = false) String category,
	            @RequestParam(value = "categoryDetail", required = false) String categoryDetail,
	            @RequestParam("name") String name,
	            @RequestParam("description") String description,
	            @RequestParam("startPrice") Integer startPrice,
	            @RequestParam("startTime") LocalDateTime startTime,
	            @RequestParam(value = "endTime", required = false) LocalDateTime endTime,
	            @RequestParam("brand") String brand,
	            @RequestParam("files") List<MultipartFile> files,
	            @RequestParam(value = "isMainImage", required = false) List<Integer> isMainImageFlags,
	            RedirectAttributes redirectAttributes) {

	        try {
	            logger.info("요청 데이터: auctionType={}, name={}, startPrice={}", auctionType, name, startPrice);
	            logger.info("파일 개수: {}, 대표 이미지 플래그 개수: {}", files.size(), isMainImageFlags.size());

	         // 대표 이미지 플래그가 누락되었거나 개수가 맞지 않을 경우 처리
	            if (isMainImageFlags == null || isMainImageFlags.size() != files.size()) {
	                logger.warn("대표 이미지 플래그 개수가 누락되었거나 파일 개수와 일치하지 않습니다. 기본값으로 설정합니다.");
	                isMainImageFlags = new ArrayList<>();
	                for (int i = 0; i < files.size(); i++) {
	                    isMainImageFlags.add(i == 0 ? 1 : 0); // 첫 번째 이미지를 기본 대표 이미지로 설정
	                }
	            }

	            // 파일과 대표 이미지 데이터 출력
	            for (int i = 0; i < files.size(); i++) {
	                MultipartFile file = files.get(i);
	                boolean isMainImage = isMainImageFlags.get(i) == 1;

	                logger.info("파일 이름: {}, 대표 이미지 여부: {}", file.getOriginalFilename(), isMainImage);
	            }

	            // DB 저장 시작
	            LiveAuctionItem liveAuctionItem = new LiveAuctionItem();
	            logger.info("userInfoId: {}", userInfoId);
	            liveAuctionItem.setUserInfoId(userInfoId);
	            liveAuctionItem.setName(name);
	            liveAuctionItem.setDescription(description);
	            liveAuctionItem.setStartPrice(startPrice);
	            liveAuctionItem.setStartTime(startTime);
	            liveAuctionItem.setBrand(brand);
	            
	            LiveAuctionItemImage liveAuctionItemImage = new LiveAuctionItemImage();
	            // 라이브 경매 등록 서비스 호출
	            if ("live".equalsIgnoreCase(auctionType)) {
	                liveAuctionRegisterService.registerLiveAuction(liveAuctionItem, files, isMainImageFlags);
	            }

	            redirectAttributes.addFlashAttribute("message", "경매 등록 성공");
	        } catch (Exception e) {
	            logger.error("오류 발생: {}", e.getMessage(), e);
	            redirectAttributes.addFlashAttribute("error", "경매 등록 실패: " + e.getMessage());
	        }

	        return "redirect:/home";
	}
	
	
	
	
	
//	public Long getAuthenticatedUserId() {
//	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//	    if (authentication.getPrincipal() instanceof OAuth2User) {
//	        // OAuth2 사용자
//	        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
//	        return Long.parseLong(oauth2User.getAttribute("id")); // OAuth2 제공자의 사용자 ID
//	    } else if (authentication.getPrincipal() instanceof UserDetails) {
//	        // 일반 로그인 사용자
//	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//	        return Long.parseLong(userDetails.getUsername()); // UserDetails에서 ID 추출
//	    } else {
//	        throw new IllegalStateException("인증된 사용자가 없습니다.");
//	    }
//	}
	
	
	
	
}
