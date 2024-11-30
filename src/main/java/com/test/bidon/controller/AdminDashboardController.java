package com.test.bidon.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.bidon.dto.MonthlyUserCountDTO;
import com.test.bidon.repository.CustomAdminDashboardRepository;
import com.test.bidon.repository.LiveAuctionItemRepository;
import com.test.bidon.repository.NormalAuctionItemRepository;
import com.test.bidon.repository.UserRepository;

@Controller
public class AdminDashboardController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CustomAdminDashboardRepository customAdminDashboardRepository;
	@Autowired
	private NormalAuctionItemRepository normalAuctionItemRepository;
	@Autowired
	private LiveAuctionItemRepository liveAuctionItemRepository;

	@GetMapping("/admin")
	public String index(Model model) {
		
		//총회원수
		long userCount = userRepository.count();
		// 30일 이내 가입한 신규 인원 수
        long newUserCount = customAdminDashboardRepository.newUserCount();
		// 월별 누적 회원 수 + 월별 신규 회원 수
        List<MonthlyUserCountDTO> monthlyUserCountList = customAdminDashboardRepository.findMonthlyUserCounts();

        List<Long> monthlyNewUserCounts = new ArrayList<>(Collections.nCopies(12, 0L));
        List<Long> monthlyExistingUserCounts = new ArrayList<>(Collections.nCopies(12, 0L));

        for (MonthlyUserCountDTO dto : monthlyUserCountList) {
            int monthIndex = Integer.parseInt(dto.getMonth()) - 1;
            monthlyNewUserCounts.set(monthIndex, dto.getCount());
            monthlyExistingUserCounts.set(monthIndex, dto.getCumulativeCount());
        }
        
//		System.out.println(monthlyUserCountList);
//		System.out.println(monthlyNewUserCounts);
//		System.out.println(monthlyExistingUserCounts);
		
		// 경매 참여자 수
		long bidEnterUserCount = customAdminDashboardRepository.getBidEnterUserCount();

		//일반 경매 상품 수
		long normalItemCount = normalAuctionItemRepository.count();
		//실시간 경매 상품 수
		long liveItemCount = liveAuctionItemRepository.count();
		//총 수익률
		long totalBidPrice = customAdminDashboardRepository.getTotalBidPrice();
		// 진행 중인 일반 경매 물품 수
		long ongoingNormalItemCount = customAdminDashboardRepository.getOngoingNormalAuctionItemCount();

		// 진행 중인 실시간 경매 물품 수
		long ongoingLiveItemCount = customAdminDashboardRepository.getOngoingLiveAuctionItemCount();

		// 총 낙찰 물품 수
		long totalWinningBidCount = customAdminDashboardRepository.getTotalWinningBidCount();
		
		//월 평균 낙찰 가격
		Double averageBidPrice = customAdminDashboardRepository.getAverageBidPrice();
		
		//월별 경매 물품 분석 리스트(진행중,종료된,등록된 경매)
//		List<MonthlyAuctionStatusDTO> list = customAdminDashboardRepository.getMonthlyAuctionStats();
		
		//월별 평균 시작 가격
//		List<Tuple> liveAuctionAvgStartPrice = customAdminDashboardRepository.getLiveAuctionMonthlyAverageStartPrice();
//		List<Tuple> normalAuctionAvgStartPrice = customAdminDashboardRepository.getNormalAuctionMonthlyAverageStartPrice();
//        model.addAttribute("liveAuctionAvgStartPrice", liveAuctionAvgStartPrice);
//        model.addAttribute("normalAuctionAvgStartPrice", normalAuctionAvgStartPrice);
//        model.addAttribute("title", "Monthly Average Start Price");
		

		
		//월별 실시간 경매 수익
//		List<Tuple> monthlyLiveAuctionRevenue =  CustomAdminDashboardRepository.getMonthlyLiveAuctionRevenue();
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
		
		model.addAttribute("bidEnterUserCount", bidEnterUserCount);
		model.addAttribute("normalItemCount", normalItemCount);
		model.addAttribute("liveItemCount", liveItemCount);
		model.addAttribute("totalBidPrice", totalBidPrice);
		model.addAttribute("averageBidPrice", averageBidPrice);

		model.addAttribute("ongoingNormalItemCount", ongoingNormalItemCount);
		model.addAttribute("ongoingLiveItemCount", ongoingLiveItemCount);

		model.addAttribute("totalWinningBidCount", totalWinningBidCount);

		return "admin/index";
	}

}
