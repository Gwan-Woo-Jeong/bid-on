package com.test.bidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.bidon.entity.OneOnOne;
import com.test.bidon.entity.UserEntity;
import com.test.bidon.repository.OneOnOneRepository;
import com.test.bidon.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin/community")
public class OneOnOneController {

    @Autowired
    private OneOnOneRepository oneOnOneRepository;

    @Autowired
    private UserRepository userRepository;

    // GET 요청: 모든 1:1 문의 리스트를 가져오기
    @GetMapping
    public String getAllQuestions(Model model) {
        List<OneOnOne> questions = oneOnOneRepository.findAll();
        model.addAttribute("questions", questions);
        return "admin/community"; // admin/community.html로 이동
    }

    // POST 요청: 새로운 1:1 문의 등록
    @PostMapping
    public String submitQuestion(
            @RequestParam("email") String email,
            @RequestParam("title") String title,
            @RequestParam("contents") String contents,
            Model model) {

        // 사용자 조회
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            // 이메일이 등록되지 않은 경우 에러 메시지 처리
            model.addAttribute("error", "등록되지 않은 이메일입니다: " + email);
            model.addAttribute("title", title); // 기존 데이터 유지
            model.addAttribute("contents", contents);
            List<OneOnOne> questions = oneOnOneRepository.findAll(); // 기존 질문 리스트 반환
            model.addAttribute("questions", questions);
            return "admin/community"; // 동일 페이지로 리턴
        }

        // 새로운 질문 엔터티 생성 및 저장
        OneOnOne question = new OneOnOne();
        question.setUserEntityInfo(userEntity);
        question.setTitle(title);
        question.setContents(contents);
        question.setRegdate(LocalDate.now());
        oneOnOneRepository.save(question);

        return "redirect:/admin/community"; // 저장 후 리다이렉트
    }
}
