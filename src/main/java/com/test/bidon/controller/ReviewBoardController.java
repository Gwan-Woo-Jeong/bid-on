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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.bidon.dto.ReviewBoardFormDTO;
import com.test.bidon.entity.ReviewBoard;
import com.test.bidon.repository.ReviewBoardRepository;
import com.test.bidon.service.FileService;
import com.test.bidon.service.ReviewBoardService;

@Controller
public class ReviewBoardController {

    @Autowired
    private ReviewBoardRepository reviewBoardRepository;

    @Autowired
    private ReviewBoardService reviewBoardService;
    
    @Autowired
    private FileService fileService;
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
    public String showAddReviewPage( Model model) {
       
        return "user/add-review"; // 폼 렌더링
    }


    @PostMapping("/add-review")
    public String addReview(@ModelAttribute ReviewBoardFormDTO form) {
        // 1. 대표 사진 저장
        String thumbnailPath = fileService.saveFile(form.getThumbnail()); // 대표 이미지 저장

        // 2. 추가 사진 저장
        List<String> additionalPhotoPaths = form.getPhotos().stream()
            .map(fileService::saveFile) // 추가 이미지 저장
            .collect(Collectors.toList());

        // 3. 서비스 호출
        reviewBoardService.addReview(
            form.getTitle(),
            form.getContents(),
            form.getEmail(),
            thumbnailPath,
            String.join(",", additionalPhotoPaths)
        );
        
        

        return "redirect:/blog"; // 작성 완료 후 블로그로 리다이렉트
    }
    
    public void addReviewFromController() {
        reviewBoardService.addReview("Title", "Content", "email@example.com", "/path/to/thumbnail", "/path/to/photos");
    }


    
   
    
}
