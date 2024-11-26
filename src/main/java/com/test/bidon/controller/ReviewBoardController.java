//package com.test.bidon.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import com.test.bidon.entity.ReviewBoard;
//import com.test.bidon.entity.UserEntity;
//import com.test.bidon.repository.ReviewBoardRepository;
//import com.test.bidon.repository.UserRepository;
//
//@Controller
//@RequestMapping("/blog")
//public class ReviewBoardController {
//
//    @Autowired
//    private ReviewBoardRepository reviewBoardRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private ReviewBoard findReviewBoardById(Integer id) {
//        return reviewBoardRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID: " + id));
//    }
//
//    @GetMapping
//    public String listReviewBoards(
//        @RequestParam(defaultValue = "0") int page, 
//        @RequestParam(defaultValue = "10") int size, 
//        Model model
//    ) {
//        Page<ReviewBoard> reviewBoards = reviewBoardRepository.findAll(PageRequest.of(page, size));
//        model.addAttribute("reviewBoards", reviewBoards);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", reviewBoards.getTotalPages());
//        return "blog/list";
//    }
//
//    @GetMapping("/create")
//    public String showCreateForm(Model model) {
//        model.addAttribute("error", null);
//        return "blog/create";
//    }
//
//    @PostMapping("/create")
//    public String createReview(
//        @RequestParam("userInfoId") Long userInfoId, 
//        @RequestParam("title") String title, 
//        @RequestParam("contents") String contents, 
//        Model model
//    ) {
//        if (title == null || title.trim().isEmpty()) {
//            model.addAttribute("error", "제목은 필수 입력 항목입니다.");
//            return "blog/create";
//        }
//        if (contents == null || contents.trim().isEmpty()) {
//            model.addAttribute("error", "내용은 필수 입력 항목입니다.");
//            return "blog/create";
//        }
//
//        UserEntity userEntity = userRepository.findById(userInfoId)
//                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID입니다."));
//
//        ReviewBoard reviewBoard = new ReviewBoard();
//        reviewBoard.setUserEntityInfo(userEntity);
//        reviewBoard.setTitle(title);
//        reviewBoard.setContents(contents);
//        reviewBoard.setViews(0);
//        reviewBoard.setRegdate(java.time.LocalDate.now());
//        reviewBoardRepository.save(reviewBoard);
//
//        return "redirect:/blog";
//    }
//
//    @GetMapping("/{id}")
//    public String viewReviewBoard(@PathVariable Integer id, Model model) {
//        ReviewBoard reviewBoard = findReviewBoardById(id);
//        reviewBoard.setViews(reviewBoard.getViews() + 1);
//        reviewBoardRepository.save(reviewBoard);
//        model.addAttribute("reviewBoard", reviewBoard);
//        return "blog/view";
//    }
//
//    @PostMapping("/{id}/delete")
//    public String deleteReviewBoard(@PathVariable Integer id) {
//        reviewBoardRepository.findById(id).ifPresent(reviewBoardRepository::delete);
//        return "redirect:/blog";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String showEditForm(@PathVariable Integer id, Model model) {
//        ReviewBoard reviewBoard = findReviewBoardById(id);
//        model.addAttribute("reviewBoard", reviewBoard);
//        model.addAttribute("error", null);
//        return "blog/edit";
//    }
//
//    @PostMapping("/{id}/update")
//    public String updateReviewBoard(
//        @PathVariable Integer id, 
//        @RequestParam("title") String title, 
//        @RequestParam("contents") String contents, 
//        Model model
//    ) {
//        if (title == null || title.trim().isEmpty()) {
//            model.addAttribute("error", "제목은 필수 입력 항목입니다.");
//            model.addAttribute("reviewBoard", findReviewBoardById(id));
//            return "blog/edit";
//        }
//        if (contents == null || contents.trim().isEmpty()) {
//            model.addAttribute("error", "내용은 필수 입력 항목입니다.");
//            model.addAttribute("reviewBoard", findReviewBoardById(id));
//            return "blog/edit";
//        }
//
//        ReviewBoard reviewBoard = findReviewBoardById(id);
//        reviewBoard.setTitle(title);
//        reviewBoard.setContents(contents);
//        reviewBoardRepository.save(reviewBoard);
//
//        return "redirect:/blog/" + id;
//    }
//}
