package com.test.bidon.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.bidon.dto.MonthlyAveragetBidPriceDTO;
import com.test.bidon.dto.MonthlyAveragetStartPriceDTO;
import com.test.bidon.dto.MonthlyItemCountDTO;
import com.test.bidon.dto.MonthlyRevenueDTO;
import com.test.bidon.dto.MonthlyUserCountDTO;
import com.test.bidon.repository.CustomAdminDashboardRepository;
import com.test.bidon.repository.CustomNormalAuctionItemRepository;
import com.test.bidon.repository.LiveAuctionItemRepository;
import com.test.bidon.repository.UserRepository;

@Controller
public class AdminDashboardController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CustomAdminDashboardRepository customAdminDashboardRepository;
	@Autowired
	private CustomNormalAuctionItemRepository customNormalAuctionItemRepository;
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
		long normalItemCount = customNormalAuctionItemRepository.count();
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
		
		
		// 월별 경매 물품 분석 리스트(진행중,종료된,등록된 경매)
		List<MonthlyItemCountDTO> monthlyItemCountList = customAdminDashboardRepository.findMonthlyItemCountList();

		List<Long> registeredCount = new ArrayList<>(Collections.nCopies(12, 0L));
		List<Long> ongoingCount = new ArrayList<>(Collections.nCopies(12, 0L));
		List<Long> endCount = new ArrayList<>(Collections.nCopies(12, 0L));

		for (MonthlyItemCountDTO dto : monthlyItemCountList) {
		    try {
		        int monthIndex = Integer.parseInt(dto.getMonth()) - 1;
		        if (monthIndex >= 0 && monthIndex < 12) {
		            registeredCount.set(monthIndex, dto.getRegisteredCount());
		            ongoingCount.set(monthIndex, dto.getOngoingCount());
		            endCount.set(monthIndex, dto.getEndCount());
		        } else {
		            System.err.println("Invalid month index: " + monthIndex);
		        }
		    } catch (NumberFormatException e) {
		        System.err.println("Invalid month value: " + dto.getMonth());
		    }
		}
		
		//월별 경매 수수료 수익
		List<MonthlyRevenueDTO> monthlyRevenueList = customAdminDashboardRepository.getMonthlyRevenueList();
		List<Long> totalRevenue = new ArrayList<>(Collections.nCopies(12, 0L));
		List<Long> cumulativeTotalRevenue = new ArrayList<>(Collections.nCopies(12, 0L));
		for (MonthlyRevenueDTO dto : monthlyRevenueList) {
		    int monthIndex = Integer.parseInt(dto.getMonth()) - 1;
		    if (monthIndex >= 0 && monthIndex < 12) {
		        totalRevenue.set(monthIndex, dto.getTotalRevenue());
		        cumulativeTotalRevenue.set(monthIndex, dto.getCumulativeTotalRevenue());
		    }
		}
		
		//경매 성과 분석---------------------------------------------------------
		// 월별 평균 시작 가격
		List<MonthlyAveragetStartPriceDTO> monthlyAverageStartPriceList = customAdminDashboardRepository.findMonthlyAverageStartPriceList();
		List<Double> monthlyAverageStartPrice = new ArrayList<>(Collections.nCopies(12, 0.0));

		for (MonthlyAveragetStartPriceDTO dto : monthlyAverageStartPriceList) {
		    try {
		        int monthIndex = Integer.parseInt(dto.getMonth()) - 1;
		        if (monthIndex >= 0 && monthIndex < 12) {
		        	monthlyAverageStartPrice.set(monthIndex, dto.getAveragetPrice());
		        }
		    } catch (NumberFormatException e) {
		        System.err.println("Invalid month value: " + dto.getMonth());
		    }
		}
		//월별 평균 낙찰 가격
		List<MonthlyAveragetBidPriceDTO> monthlyAverageBidPriceList = customAdminDashboardRepository.findMonthlyAverageBidPriceList();
		List<Double> monthlyAverageBidPrice = new ArrayList<>(Collections.nCopies(12, 0.0));

		for (MonthlyAveragetBidPriceDTO dto : monthlyAverageBidPriceList) {
		    try {
		        int monthIndex = Integer.parseInt(dto.getMonth()) - 1;
		        if (monthIndex >= 0 && monthIndex < 12) {
		        	monthlyAverageBidPrice.set(monthIndex, dto.getAveragetPrice());
		        }
		    } catch (NumberFormatException e) {
		        System.err.println("Invalid month value: " + dto.getMonth());
		    }
		}

		//총 회원 수
		model.addAttribute("userCount", userCount);
		//30일 이내 가입한 신규 회원 수
		model.addAttribute("newUserCount", newUserCount);
		//월별 누적 회원 수
		model.addAttribute("monthlyNewUserCounts", monthlyNewUserCounts);
		//월별 신규 회원 수
		model.addAttribute("monthlyExistingUserCounts", monthlyExistingUserCounts);
		//월별 누적회원수 + 신규 회원 수 리스트
		model.addAttribute("monthlyUserCountList", monthlyUserCountList);
		//경매 참여자수
		model.addAttribute("bidEnterUserCount", bidEnterUserCount);
		//일반 경매 상품 수
		model.addAttribute("normalItemCount", normalItemCount);
		//실시간 경매 상품 수
		model.addAttribute("liveItemCount", liveItemCount);
		//
		model.addAttribute("totalBidPrice", totalBidPrice);
		//진행중인 일반 경매 물품수
		model.addAttribute("ongoingNormalItemCount", ongoingNormalItemCount);
		//진행중인 실시간 경매 물품수
		model.addAttribute("ongoingLiveItemCount", ongoingLiveItemCount);
		//총 낙찰 물품수
		model.addAttribute("totalWinningBidCount", totalWinningBidCount);
		//총 평균 낙찰 가격
		model.addAttribute("averageBidPrice", averageBidPrice);
		// 월별 경매 물품 분석 리스트(진행중,종료된,등록된 경매)
		model.addAttribute("monthlyItemCountList", monthlyItemCountList);
		model.addAttribute("registeredCount", registeredCount);
		model.addAttribute("ongoingCount", ongoingCount);
		model.addAttribute("endCount", endCount);
		
		//월별 경매 수수료 수익
		model.addAttribute("totalRevenue", totalRevenue);
		model.addAttribute("cumulativeTotalRevenue", cumulativeTotalRevenue);
		//월별 평균 시작 가격
		model.addAttribute("monthlyAverageStartPrice", monthlyAverageStartPrice);
		//월별 평균 낙찰 가격
		model.addAttribute("monthlyAverageBidPrice", monthlyAverageBidPrice);
		
		return "admin/index";
	}
	
	
}
