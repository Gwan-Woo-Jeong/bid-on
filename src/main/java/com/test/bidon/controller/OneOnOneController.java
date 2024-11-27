package com.test.bidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.bidon.entity.OneOnOne;
import com.test.bidon.entity.UserEntity;
import com.test.bidon.repository.OneOnOneRepository;
import com.test.bidon.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/oneonone")  // 기본 경로 변경	>  에러나서 이혜미가 추가함
public class OneOnOneController {

    @Autowired
    private OneOnOneRepository oneOnOneRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 질문 조회 페이지
     */
    @GetMapping
//    public String getAllQuestions(Model model) {
//        List<OneOnOne> questions = oneOnOneRepository.findAll();
//        model.addAttribute("questions", questions);
//        return "admin/community"; // admin/community.html로 이동
//    }

    /**
     * 질문 등록 처리
     */
    @PostMapping("/answerList")
    public String submitQuestion(
            @RequestParam("email") String email,
            @RequestParam("title") String title,
            @RequestParam("contents") String contents,
            RedirectAttributes redirectAttributes) {

        try {
            // 사용자 조회
            UserEntity userEntity = userRepository.findByEmail(email);
            if (userEntity == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "등록되지 않은 이메일입니다: " + email);
                return "redirect:/admin/community"; // 등록 실패 시 질문 조회 페이지로 리다이렉트
            }

            // 새로운 질문 엔터티 생성 및 저장
            OneOnOne question = new OneOnOne();
            question.setUserEntityInfo(userEntity);
            question.setTitle(title);
            question.setContents(contents);
            question.setRegdate(LocalDate.now());
            oneOnOneRepository.save(question);

            redirectAttributes.addFlashAttribute("successMessage", "질문이 성공적으로 등록되었습니다!");

            // 등록 성공 후 /faq 페이지로 이동
            return "redirect:/faq";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "질문 등록 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/admin/community";
        }
    }
}
