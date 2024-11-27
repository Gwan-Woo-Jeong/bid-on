package com.test.bidon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.test.bidon.entity.ReviewBoard;
import com.test.bidon.repository.ReviewBoardRepository;

@Controller
public class ReviewBoardController {

    @Autowired
    private ReviewBoardRepository reviewBoardRepository;

    /**
     * 블로그 페이지 - 페이징 처리
     */
    @GetMapping("/blog")
    public String getBlogPage(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        int pageSize = 6;
        Page<ReviewBoard> reviews = reviewBoardRepository.findAll(PageRequest.of(page, pageSize));
        
        // Lazy Loading 방지를 위해 엔티티 데이터 명시적 초기화
        List<Map<String, Object>> reviewList = reviews.getContent().stream().map(review -> {
            Map<String, Object> map = new HashMap<>();
            map.put("title", review.getTitle());
            map.put("contents", review.getContents());
            map.put("views", review.getViews());
            map.put("name", review.getUserEntityInfo().getName()); // 사용자 이름
            return map;
        }).collect(Collectors.toList());

        model.addAttribute("reviews", reviewList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", reviews.getTotalPages());
        return "user/blog";
    }


    /**
     * 블로그 상세 페이지
     */
//    @GetMapping("/blog-detail")
//    public String getBlogDetail(@RequestParam(name = "id") Integer id, Model model) {
//        ReviewBoard review = reviewBoardRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID: " + id));
//
//        // 조회수 증가
//        review.incrementViews();
//        reviewBoardRepository.save(review);
//
//        model.addAttribute("review", review); // 상세 정보
//        return "user/blog-detail"; // 상세 페이지 템플릿 경로
//    }
}
