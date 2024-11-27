package com.test.bidon.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.bidon.dto.MonthlyUserCountDTO;
import com.test.bidon.repository.AdminDashboardRepository;
import com.test.bidon.repository.NormalAuctionItemRepository;
import com.test.bidon.repository.UserRepository;

@Controller
public class AdminDashboardController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AdminDashboardRepository adminDashboardRepository;
	@Autowired
	private NormalAuctionItemRepository normalAuctionItemRepository;
	//@Autowired
	//private LiveAuctionItemRepository liveAuctionItemRepository;
	
	@GetMapping("/admin")
	public String index(Model model) {
		
		//총회원수
		long userCount = userRepository.count();
		// 30일 이내 가입한 신규 인원 수
        long newUserCount = adminDashboardRepository.newUserCount();
        // 월별 누적 회원 수 + 신규 회원 수
        List<MonthlyUserCountDTO> monthlyUserCountList = adminDashboardRepository.findMonthlyUserCounts(); // MonthlyUserCountDTO 리스트로 변경

        System.out.println(monthlyUserCountList);
        
        
		//일반 경매 상품 수
		long nomalItemCount = normalAuctionItemRepository.count();
		
		
		
		model.addAttribute("userCount", userCount);
		model.addAttribute("newUserCount", newUserCount);
        model.addAttribute("monthlyUserCountList", monthlyUserCountList);
		model.addAttribute("nomalItemCount", nomalItemCount);
		
		return "admin/index";
	}
	
}
