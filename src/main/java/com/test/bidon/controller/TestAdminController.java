package com.test.bidon.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.bidon.dto.NormalAuctionItemDTO;
import com.test.bidon.dto.NormalAuctionWishDTO;
import com.test.bidon.dto.UserInfoDTO;
import com.test.bidon.entity.LiveAuctionItem;
import com.test.bidon.entity.NormalAuctionItem;
import com.test.bidon.entity.OneOnOne;
import com.test.bidon.entity.ReviewBoard;
import com.test.bidon.entity.UserEntity;
import com.test.bidon.repository.CustomNormalAuctionItemRepository;
import com.test.bidon.repository.LiveAuctionItemRepository;
import com.test.bidon.repository.NormalAuctionItemRepository;
import com.test.bidon.repository.OneOnOneRepository;
import com.test.bidon.repository.ReviewBoardRepository;
import com.test.bidon.repository.UserRepository;
import com.test.bidon.service.TodayAddItem;

@Controller
public class TestAdminController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ReviewBoardRepository reviewBoardRepository;
	@Autowired
	private OneOnOneRepository oneOnOneRepository;
	@Autowired
	private NormalAuctionItemRepository normalAuctionItemRepository;
	@Autowired
	private LiveAuctionItemRepository liveAuctionItemRepository;
	@Autowired
	private TodayAddItem todayAddItem;
	@Autowired
	private CustomNormalAuctionItemRepository customNormalAuctionItemRepository;

	//경매 관리
	@GetMapping("/admin/auction")
	public String auctionPage(Model model) {
		
		LocalDateTime currentTime = LocalDateTime.now();
		model.addAttribute("currentTime", currentTime);
	    
		
	    List<NormalAuctionItem> normalList = normalAuctionItemRepository.findAll(Sort.by(Sort.Order.desc("startTime")));
	    for (NormalAuctionItem item : normalList) {
            item.calculateStatus(currentTime);  
        }
	    model.addAttribute("normalList", normalList);
	    
	    
	    List<NormalAuctionItem> normalProgressList = normalAuctionItemRepository.findAll(Sort.by(Sort.Order.desc("startTime")));

	    List<NormalAuctionItem> filteredNormalProgressList = normalProgressList.stream()
                .filter(item -> item.getStartTime().isBefore(currentTime) && item.getEndTime().isAfter(currentTime))
                .collect(Collectors.toList());
        model.addAttribute("normalProgressList", filteredNormalProgressList);
	    

	    List<LiveAuctionItem> liveList = liveAuctionItemRepository.findAll(Sort.by(Sort.Order.desc("startTime")));
	    for (LiveAuctionItem item : liveList) {
            item.calculateStatus(currentTime);  
        }
	    model.addAttribute("liveList", liveList);
	    
	    
	    List<LiveAuctionItem> progressList = liveAuctionItemRepository.findAll(Sort.by(Sort.Order.desc("startTime")));

	    
	    List<LiveAuctionItem> filteredProgressList = progressList.stream()
                .filter(item -> item.getStartTime().isBefore(currentTime) && item.getEndTime().isAfter(currentTime))
                .collect(Collectors.toList());
        model.addAttribute("progressList", filteredProgressList);
        
        
        List<NormalAuctionItem> todayNormalList = todayAddItem.TodayAddNormalItem(normalList, currentTime);
        model.addAttribute("todayNormalList", todayNormalList);  // 오늘 등록된 normal 경매 아이템만 추가


        // 오늘 등록된 LiveAuctionItem만 필터링
        List<LiveAuctionItem> todayLiveList = todayAddItem.TodayAddLiveItems(liveList, currentTime);
        model.addAttribute("todayLiveList", todayLiveList); 
        
        
	    List<NormalAuctionWishDTO> wishList = customNormalAuctionItemRepository.getWishCountStats();
	    model.addAttribute("wishList", wishList);
	    
	    
	    return "admin/auction";
	}

	
	//회원 관리 > 회원 리스트
	@GetMapping("/admin/user")
	public String userList(Model model) {
		List<UserEntity> userList = userRepository.findAll();
		model.addAttribute("userList", userList);
	    return "admin/user";
	}
	
	//회원 관리 > 회원 검색
	@GetMapping("/search")
	@ResponseBody
	public ResponseEntity<List<UserEntity>> searchUser(
	        @RequestParam(name = "name", required = false) String name, 
	        @RequestParam(name = "createDate", required = false) String createDate,
	        @RequestParam(name = "national", required = false) String national,
	        @RequestParam(name = "birth", required = false) String birth,
	        @RequestParam(name = "status", required = false) String status) {

	    List<UserEntity> userList = userRepository.findAll();

	    
	    if (name != null && !name.isEmpty()) {
	        userList = userList.stream()
	                           .filter(user -> user.getName() != null && user.getName().contains(name))  
	                           .collect(Collectors.toList());
	    }

	    
	    if (createDate != null && !createDate.isEmpty()) {
	        try {
	            LocalDate parsedJoinDate = LocalDate.parse(createDate);
	            userList = userList.stream()
	                               .filter(user -> user.getCreateDate() != null && user.getCreateDate().equals(parsedJoinDate)) 
	                               .collect(Collectors.toList());
	        } catch (DateTimeParseException e) {
	         
	            return ResponseEntity.badRequest().body(null);
	        }
	    }

	    if (national != null && !national.isEmpty()) {
	        userList = userList.stream()
	                           .filter(user -> user.getNational() != null && user.getNational().equals(national))  
	                           .collect(Collectors.toList());
	    }

	    if (birth != null && !birth.isEmpty()) {
	        try {
	            LocalDate parsedBirthDate = LocalDate.parse(birth);
	            userList = userList.stream()
	                               .filter(user -> user.getBirth() != null && user.getBirth().equals(parsedBirthDate)) 
	                               .collect(Collectors.toList());
	        } catch (DateTimeParseException e) {
	       
	            return ResponseEntity.badRequest().body(null);
	        }
	    }

	    
	    if (status != null && !status.isEmpty()) {
	        userList = userList.stream()
	                           .filter(user -> user.getStatus() != null && user.getStatus().toString().equals(status)) 
	                           .collect(Collectors.toList());
	    }

	    if (userList.isEmpty()) {
	        return ResponseEntity.noContent().build();  
	    }
	    return ResponseEntity.ok(userList);
	}
	
	@GetMapping("/admin/userInfo")
	@ResponseBody
	public ResponseEntity<UserInfoDTO> getUserInfo(@RequestParam(name = "id") Long id) {

	    UserEntity user = userRepository.findById(id).orElse(null);
	    
	    UserInfoDTO dto = user.toDTO();

	    return ResponseEntity.ok(dto);
	}
	
	
	//커뮤니티 관리 > 리스트 조회
	@GetMapping("/admin/community")
	public String communityPage(Model model) {
	    List<ReviewBoard> reviewList = reviewBoardRepository.findAll(Sort.by(Sort.Order.desc("regdate")));
	    model.addAttribute("reviewList", reviewList); 
	    
	    List<OneOnOne> questionsList = oneOnOneRepository.findAll(Sort.by(Sort.Order.desc("regdate")));
        model.addAttribute("questionsList", questionsList);
	    
	    return "admin/community";  // admin/community 페이지로 이동
	}

}