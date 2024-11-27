package com.test.bidon.controller;

import java.util.Arrays;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        // ID와 사진 번호 매핑
        Map<Integer, Integer> idToPhotoMap = Map.ofEntries(
        	    Map.entry(1, 1),
        	    Map.entry(2, 3),
        	    Map.entry(3, 5),
        	    Map.entry(4, 8),
        	    Map.entry(5, 10),
        	    Map.entry(6, 11),
        	    Map.entry(7, 12),
        	    Map.entry(8, 13),
        	    Map.entry(9, 15),
        	    Map.entry(10, 16),
        	    Map.entry(11, 18),
        	    Map.entry(12, 19)
        	);

        // 리뷰 데이터를 가공하여 대표사진 경로 추가
        List<Map<String, Object>> reviewList = reviews.getContent().stream().map(review -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", review.getId());
            map.put("title", review.getTitle());
            map.put("contents", review.getContents());
            map.put("views", review.getViews());
            map.put("name", review.getUserEntityInfo().getName());

            // ID에 맞는 사진 번호 찾기
            int photoNumber = idToPhotoMap.getOrDefault(review.getId(), 1); // 기본값 1번 사진
            String thumbnailPath = "/user/images/review/reviewPhoto" + String.format("%03d", photoNumber) + ".png";
            map.put("thumbnailPath", thumbnailPath);

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
    
    
    
 //Community에서 상세보기로 넘어가는 페이지 Admin 사용예정 -민지
//    @GetMapping("/blog-detail/{id}")
//    public String reviewDetail(@PathVariable("id") Integer id, Model model) {
//        Optional<ReviewBoard> review = reviewBoardRepository.findById(id);
//        
//        if (review.isPresent()) {
//            model.addAttribute("review", review.get());
//            return "blog-detail";  
//        } else {
//            return "redirect:/admin/community"; 
//        }
//    }

}
