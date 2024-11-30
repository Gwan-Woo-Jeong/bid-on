package com.test.bidon.controller;

import com.querydsl.core.Tuple;
import com.test.bidon.dto.NormalAuctionItemDTO;
import com.test.bidon.entity.NormalAuctionItem;
import com.test.bidon.repository.CustomNormalAuctionItemRepository;
import com.test.bidon.repository.NormalAuctionItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NormalAuctionController {

    private final NormalAuctionItemRepository normalAuctionItemRepository;
    private final CustomNormalAuctionItemRepository customNormalAuctionItemRepository;


    // Oracle DB의 일반경매 물품 데이터를 가져와서 browse-bid 페이지에 출력하는 method
    // 페이지 당 9개의 물품이 출력되도록 페이징 적용
    @GetMapping("browse-bid")
    public String displayNormalAuctionItems(Model model, @RequestParam(defaultValue = "1", name = "page") Integer page) {

        // 페이징을 하기 위한 클래스
        StringBuilder sb = new StringBuilder();

        // limit == 페이지 하나에 물품이 limit 값만큼 보이게 설정
        // offset == 시작 인덱스
        int offset = (page - 1) * 9;
        List<Tuple> itemList = customNormalAuctionItemRepository.joinTablesToDisplayBrowseBid(offset, 9);
        //System.out.println(itemList);

        // tupleList.length
        Long count = customNormalAuctionItemRepository.count(offset, 9);
        //System.out.println(count);

        List<Tuple> itemStatusList = customNormalAuctionItemRepository.getTimeAndPrice();
        //System.out.println(itemStatusList);
        //System.out.println(itemStatusList.get(0));
        //System.out.println(itemStatusList.get(0).get(1, String.class));
        //System.out.println(itemStatusList.size());

        for (int i = 1; i <= (int)Math.ceil(count/9.0); i++) {
            sb.append(String.format("<li class=\"page-item\"><a class=\"page-link\" href=\"browse-bid?page=%d\">%d</a></li>", i, i));
        }

        model.addAttribute("itemList", itemList);
        model.addAttribute("itemStatusList", itemStatusList);
        model.addAttribute("sb", sb.toString());

        return "user/browse-bid";
    }
}
