//package com.test.bidon.service;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//import org.springframework.stereotype.Service;
//
//import com.test.bidon.dto.MonthlyAverageStartPriceDTO;
//import com.test.bidon.repository.LiveAuctionItemRepository;
//import com.test.bidon.repository.NormalAuctionItemRepository;
//
//@Service
//public class AdminDashboardService {
//	
//	private LiveAuctionItemRepository liveAuctionItemRepository;
//    private NormalAuctionItemRepository normalAuctionItemRepository;
//
//    public void AuctionService(LiveAuctionItemRepository liveAuctionItemRepository,
//                          NormalAuctionItemRepository normalAuctionItemRepository) {
//        this.liveAuctionItemRepository = liveAuctionItemRepository;
//        this.normalAuctionItemRepository = normalAuctionItemRepository;
//    }
//
//
//    public List<MonthlyAverageStartPriceDTO> getLiveAuctionMonthlyAverageStartPrice() {
//        List<MonthlyAverageStartPriceDTO> rawData = liveAuctionItemRepository.findMonthlyAverageStartPrice();
//        return fillMissingMonths(rawData);
//    }
//
//    public List<MonthlyAverageStartPriceDTO> getNormalAuctionMonthlyAverageStartPrice() {
//        List<MonthlyAverageStartPriceDTO> rawData = normalAuctionItemRepository.findMonthlyAverageStartPrice();
//        return fillMissingMonths(rawData);
//    }
//
//    /**
//     * 모든 월(1~12)을 포함하도록 데이터를 채웁니다.
//     * @param rawData QueryDSL로 조회한 원본 데이터
//     * @return 모든 월 데이터를 포함한 List
//     */
//    private List<MonthlyAverageStartPriceDTO> fillMissingMonths(List<MonthlyAverageStartPriceDTO> rawData) {
//        // 기본 월 데이터를 생성 (1월~12월, 평균 가격은 0으로 초기화)
//        Map<String, MonthlyAverageStartPriceDTO> fullMonthMap = IntStream.rangeClosed(1, 12)
//                .mapToObj(month -> String.format("%02d", month))
//                .collect(Collectors.toMap(
//                        month -> month,
//                        month -> new MonthlyAverageStartPriceDTO(month, 0.0)
//                ));
//
//        // QueryDSL 데이터로 기본 데이터를 덮어씀
//        for (MonthlyAverageStartPriceDTO data : rawData) {
//            fullMonthMap.put(data.getMonth(), data);
//        }
//
//        // 월 순서대로 List로 반환
//        return fullMonthMap.values()
//                .stream()
//                .sorted(Comparator.comparing(MonthlyAverageStartPriceDTO::getMonth))
//                .collect(Collectors.toList());
//    }
//}
