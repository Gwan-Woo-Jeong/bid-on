package com.test.bidon.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.bidon.dto.LiveAuctionDetailDTO;
import com.test.bidon.dto.LiveAuctionDetailImagesDTO;
import com.test.bidon.dto.LiveAuctionItemListDTO;
import com.test.bidon.dto.LiveBidRoomItemDTO;
import com.test.bidon.repository.CustomLiveAuctionItemRepository;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/live-auction")
public class LiveAuctionController {

    private final CustomLiveAuctionItemRepository liveAuctionItemRepository;

    @GetMapping("/list")
    public String browseLiveBid(Model model,
            @RequestParam(defaultValue = "1", name = "page") Integer page,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice) {

			// 가격이 NaN인 경우 기본값 설정
			if (minPrice == null || minPrice < 0) {
			minPrice = 100000; // 기본 최소값
			}
			if (maxPrice == null || maxPrice < 0) {
			maxPrice = 5000000; // 기본 최대값
			}
			
			int pageSize = 15; // 한 페이지당 항목 수
			int offset = (page - 1) * pageSize;
			
			List<LiveAuctionItemListDTO> liveItemList = liveAuctionItemRepository.LiveAuctionList(offset, pageSize, minPrice, maxPrice);
			Long count = liveAuctionItemRepository.LiveAuctionListPage(offset, pageSize, minPrice, maxPrice);
			
			int totalPages = count == 0 ? 1 : (int) Math.ceil(count / (double) pageSize);

			
			List<Integer> pageNumbers = new ArrayList<>();
			for (int i = 1; i <= totalPages; i++) {
			pageNumbers.add(i);
			}
			
			if (pageNumbers.isEmpty()) {
		        pageNumbers.add(1);
		    }
			
			
			  System.out.println("Total Items (count): " + count);
			    System.out.println("Total Pages: " + totalPages);
			    System.out.println("Page Numbers: " + pageNumbers);

			
			model.addAttribute("liveItemList", liveItemList);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("pageNumbers", pageNumbers);
			model.addAttribute("minPrice", minPrice);
			model.addAttribute("maxPrice", maxPrice);
			
			return "user/browse-live-bid";
    }
    
    
    

    @GetMapping("/detail")
    public String bidDetailLive(Model model, String itemId) {

        List<LiveAuctionDetailImagesDTO> detailImages = liveAuctionItemRepository.detailImages(Long.valueOf(itemId));

        LiveAuctionDetailDTO liveAuctionDetail = liveAuctionItemRepository.getAuctionDetail(Long.valueOf(itemId));

        model.addAttribute("detail", liveAuctionDetail);
        model.addAttribute("images", detailImages);

        System.out.println("images: " + detailImages); // 데이터 확인

        System.out.println("Images size: " + detailImages.size());

        return "user/bid-detail-live";
    }

    @GetMapping("/bid-room")
    public String bidLive(Model model, String itemId) {

        LiveBidRoomItemDTO liveBidRoomItemInfo = liveAuctionItemRepository.getLiveBidRoomItem(Long.valueOf(itemId));

        model.addAttribute("itemInfo", liveBidRoomItemInfo);

        return "user/bid-live";

    }

//	@GetMapping("/{id}")
//	@ResponseBody // JSON 형식으로 반환
//	public LiveAuctionDetailDTO getBidDetailLiveAsJson(@PathVariable("id") Long id) {
//	    return liveAuctionItemRepository.getAuctionDetail(id);
//	}
//

}
