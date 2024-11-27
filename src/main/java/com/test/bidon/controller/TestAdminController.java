package com.test.bidon.controller;

import java.time.LocalDate;
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

import com.test.bidon.entity.OneOnOne;
import com.test.bidon.entity.ReviewBoard;
import com.test.bidon.entity.UserEntity;
import com.test.bidon.repository.OneOnOneRepository;
import com.test.bidon.repository.ReviewBoardRepository;
import com.test.bidon.repository.UserRepository;

@Controller

public class TestAdminController {

//	@GetMapping("/admin")
//	public String redirect(Model model) {
//
//		return "redirect:/admin/index";
//	}

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ReviewBoardRepository reviewBoardRepository;
	@Autowired
	private OneOnOneRepository oneOnOneRepository;
	
	@GetMapping("/admin")
	public String index(Model model) {
		
		return "admin/index";
	}
	
	@GetMapping("/admin/auction")
	public String auction(Model model) {
		
		return "admin/auction";
	}
	
	@GetMapping("/admin/user")
	public String userList(Model model) {
	List<UserEntity> userList = userRepository.findAll();
	model.addAttribute("userList", userList);
	    return "admin/user";
	}
	
	
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

	    // 국적 필터링
	    if (national != null && !national.isEmpty()) {
	        userList = userList.stream()
	                           .filter(user -> user.getNational() != null && user.getNational().equals(national))  
	                           .collect(Collectors.toList());
	    }

	    // 생일 필터링
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
	
	
	
	@GetMapping("/admin/community")
	public String communityPage(Model model) {
	    List<ReviewBoard> reviewList = reviewBoardRepository.findAll(Sort.by(Sort.Order.desc("regdate")));

	    model.addAttribute("reviewList", reviewList);  // reviewList 모델에 추가
	    
	    List<OneOnOne> questions = oneOnOneRepository.findAll();
        model.addAttribute("questions", questions);
	    
	    return "admin/community";  // admin/community 페이지로 이동
	}

	
	/*
	  @GetMapping("/admin/community") 
	  public String community(Model model) {
	  
	  	return "admin/community"; 
	  }
	 */
	
	@GetMapping("/admin/login")
	public String login(Model model) {
		
		return "admin/login";
	}
	
}

