package com.test.bidon.controller;

import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.querydsl.core.Tuple;
import com.test.bidon.dto.NormalAuctionItemWithImgDTO;
import com.test.bidon.dto.NormalBidInfoDTO;
import com.test.bidon.repository.CustomNormalAuctionItemRepository;
import com.test.bidon.repository.NormalAuctionItemDetailRepository;
import com.test.bidon.repository.NormalAuctionItemWithImg;

import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class NormalAuctionController {

    private final CustomNormalAuctionItemRepository customNormalAuctionItemRepository;
    private final NormalAuctionItemDetailRepository normalAuctionItemDetailRepository;
    private final NormalAuctionItemWithImg normalAuctionItemWithImg;


    // Oracle DB의 일반경매 물품 데이터를 가져와서 browse-bid 페이지에 출력하는 method
    // 페이지 당 9개의 물품이 출력되도록 페이징 적용
    @GetMapping("browse-bid/electro")
    public String displayNormalAuctionItems(Model model, @RequestParam(defaultValue = "1", name = "page") Integer page) {

        // 페이징을 하기 위한 StringBuilder 클래스 선언
        StringBuilder sb = new StringBuilder();

        // limit == 페이지 하나에 물품이 limit 값만큼 보이게 설정
        // offset == 시작 인덱스
        int offset = (page - 1) * 9;
        List<Tuple> itemList = customNormalAuctionItemRepository.joinTablesToDisplayBrowseBid(offset, 9);

        // itemList.length
        Long count = customNormalAuctionItemRepository.count(offset, 9);

        List<Tuple> itemStatusList = customNormalAuctionItemRepository.getTimeAndPrice();

        // StringBuilder로 페이지 css를 만드는 코드
        for (int i = 1; i <= (int)Math.ceil(count/9.0); i++) {
            sb.append(String.format("<li class=\"page-item\"><a class=\"page-link\" href=\"electro?page=%d\">%d</a></li>", i, i));
        }

        // "이런 물품은 어떠세요?" 용 randomIndex 를 만들어보자.
        Random random = new Random();
        int randomCount = 3;
        List<Integer> randomIndexes = new ArrayList<>();

        // randomIndex 3개를 만들고 randomIndexes에 집어넣기
        for (int i = 0; i < randomCount; i++) {
            int randomIndex = random.nextInt(itemList.size());
            randomIndexes.add(randomIndex);
        }

        List<Tuple> recentList = customNormalAuctionItemRepository.DisplaySixItems();
        List<Tuple> recentStatusList = customNormalAuctionItemRepository.DisplaySixTimeAndPrice();

        model.addAttribute("itemList", itemList);
        model.addAttribute("itemStatusList", itemStatusList);
        model.addAttribute("sb", sb.toString());
        model.addAttribute("randomIndexes", randomIndexes);
        model.addAttribute("recentList", recentList);
        model.addAttribute("recentStatusList", recentStatusList);

        return "user/browse-bid";
    }




    //	@GetMapping("/bid-detail")
//	public String bidDetail(Model model) {
//		List<NormalBidInfoDTO> bidinfoList = normalAuctionItemDetailRepository.ItemDetail();
//
//		model.addAttribute("bidinfoList", bidinfoList);
//
//		return "user/bid-detail";
//	}
    @GetMapping("/bid-detail/{id}")
    public String bidDetail(@PathVariable("id") Long id, Model model) {

        System.out.println("Searching for bid info with id: " + id);

        NormalBidInfoDTO bidinfo = normalAuctionItemDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bid ID"));  // Optional에서 orElseThrow 사용

        List<NormalAuctionItemWithImgDTO> itemImgs = normalAuctionItemWithImg.findItemImg();

        List<String> filteredImages = itemImgs.stream()
                .filter(item -> item.getId().equals(id))
                .flatMap(item -> item.getImagePath().stream())
                .collect(Collectors.toList());

        model.addAttribute("bidinfo", bidinfo);
        model.addAttribute("itemImgs", filteredImages);	//이미지..

        return "user/bid-detail";
    }

}
