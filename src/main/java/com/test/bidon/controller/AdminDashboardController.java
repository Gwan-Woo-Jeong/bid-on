package com.test.bidon.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.bidon.dto.MonthlyUserCountDTO;
import com.test.bidon.repository.AdminDashboardRepository;
import com.test.bidon.repository.LiveAuctionItemRepository;
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
	@Autowired
	private LiveAuctionItemRepository liveAuctionItemRepository;


	@GetMapping("/admin")
	public String index(Model model) {
		
		//총회원수
		long userCount = userRepository.count();
		// 30일 이내 가입한 신규 인원 수
        long newUserCount = adminDashboardRepository.newUserCount();
		// 월별 누적 회원 수 + 월별 신규 회원 수
		List<MonthlyUserCountDTO> monthlyUserCountList = adminDashboardRepository.findMonthlyUserCounts(); // MonthlyUserCountDTO 리스트로 변경

		// 신규 가입자 수와 기존 가입자 수를 저장할 리스트 초기화
		List<Long> monthlyNewUserCounts = new ArrayList<>(Collections.nCopies(12, 0L));
		List<Long> monthlyExistingUserCounts= new ArrayList<>(Collections.nCopies(12, 0L));

		for (MonthlyUserCountDTO dto : monthlyUserCountList) {
			int month = Integer.parseInt(dto.getMonth()); // 이미 정수형으로 저장되어 있음
			int monthIndex = month - 1; // 0부터 시작하는 인덱스

			if (monthIndex >= 0 && monthIndex < 12) {
				monthlyNewUserCounts.set(monthIndex, dto.getCount());
				monthlyExistingUserCounts.set(monthIndex, dto.getCumulativeCount());
			}
		}
		// 경매 참여자 수


		//일반 경매 상품 수
		long normalItemCount = normalAuctionItemRepository.count();
		//실시간 경매 상품 수
		long liveItemCount = liveAuctionItemRepository.count();

		// 진행 중인 일반 경매 물품 수
		long ongoingNormalItemCount = adminDashboardRepository.getOngoingNormalAuctionItemCount();

		// 진행 중인 실시간 경매 물품 수
		long ongoingLiveItemCount = adminDashboardRepository.getOngoingLiveAuctionItemCount();

		// 총 낙찰 물품 수
		//long totalWinningBidCount = adminDashboardRepository.getTotalWinningBidCount();
		//model.addAttribute("totalWinningBidCount", totalWinningBidCount);



		//월별 실시간 경매 수익
//		List<Tuple> monthlyLiveAuctionRevenue =  AdminDashboardRepository.getMonthlyLiveAuctionRevenue();
//
//		List<Integer> months = new ArrayList<>();
//		List<BigDecimal> revenues = new ArrayList<>();
//
//		for (Tuple tuple : monthlyLiveAuctionRevenue) {
//			months.add(tuple.get(0, Integer.class));
//			revenues.add(tuple.get(1, BigDecimal.class));
//		}
//
//		model.addAttribute("months", months);
//		model.addAttribute("revenues", revenues);



		model.addAttribute("userCount", userCount);
		model.addAttribute("newUserCount", newUserCount);

		model.addAttribute("monthlyNewUserCounts", monthlyNewUserCounts);
		model.addAttribute("monthlyExistingUserCounts", monthlyExistingUserCounts);
		model.addAttribute("monthlyUserCountList", monthlyUserCountList);

		model.addAttribute("normalItemCount", normalItemCount);
		model.addAttribute("liveItemCount", liveItemCount);

		model.addAttribute("ongoingNormalItemCount", ongoingNormalItemCount);
		model.addAttribute("ongoingLiveItemCount", ongoingLiveItemCount);



		return "admin/index";
	}
//
//	// 월 이름을 인덱스로 변환하는 메서드
//	private int getMonthIndex(int month) {
//		if (month >= 1 && month <= 12) {
//			return month - 1; // 1월은 0 인덱스, 2월은 1 인덱스 등으로 변환
//		}
//		return -1; // 잘못된 월 번호
//
//	}
}
