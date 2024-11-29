package com.test.bidon.controller;

import com.test.bidon.dto.*;
import com.test.bidon.repository.CustomLiveAuctionItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/live-auction")
public class LiveAuctionController {

    private final CustomLiveAuctionItemRepository liveAuctionItemRepository;

    @GetMapping("/list")
    public String browseLiveBid(Model model) {

        List<LiveAuctionItemListDTO> liveItemList = liveAuctionItemRepository.LiveAuctionList();

        model.addAttribute("liveItemList", liveItemList);

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
        Long totalBidCount = liveAuctionItemRepository.getTotalBidCount(Long.valueOf(itemId));
        Integer lastBidPrice = liveAuctionItemRepository.getLastBidPrice(Long.valueOf(itemId));
        LiveBidRoomUserDTO lastBidder = liveAuctionItemRepository.getLastBidder(Long.valueOf(itemId));
        // List<LiveBidRoomUserDTO> liveBidRoomParticipants = liveAuctionItemRepository.getLiveBidRoomParticipants(Long.valueOf(itemId));

        model.addAttribute("itemInfo", liveBidRoomItemInfo);
        model.addAttribute("totalBidCount", totalBidCount);
        model.addAttribute("lastBidPrice", lastBidPrice);
        model.addAttribute("lastBidder", lastBidder);
        // model.addAttribute("participants", liveBidRoomParticipants);

        return "user/bid-live";

    }

//	@GetMapping("/{id}")
//	@ResponseBody // JSON 형식으로 반환
//	public LiveAuctionDetailDTO getBidDetailLiveAsJson(@PathVariable("id") Long id) {
//	    return liveAuctionItemRepository.getAuctionDetail(id);
//	}
//

}
