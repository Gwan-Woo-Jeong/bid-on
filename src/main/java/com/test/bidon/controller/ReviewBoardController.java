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
import org.springframework.web.bind.annotation.PostMapping;
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
    
    @GetMapping("/add-review")
    public String showAddReviewPage() {
    	
    	
    	return "user/add-review";
    }
    
//    @PostMapping("/submit-review")
//    public String submitReview(
//            @RequestParam("title") String title,
//            @RequestParam("author") String author,
//            @RequestParam("content") String content,
//            @RequestParam(value = "image", required = false) MultipartFile image) {
//        
//        // 제출된 데이터 처리 로직 (DB 저장, 파일 저장 등)
//        System.out.println("Title: " + title);
//        System.out.println("Author: " + author);
//        System.out.println("Content: " + content);
//        if (image != null && !image.isEmpty()) {
//            System.out.println("Image: " + image.getOriginalFilename());
//        }
//
//        // 작성 후 블로그 목록으로 리다이렉트
//        return "redirect:/blog";
//    }


}
