package com.test.bidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.bidon.entity.OneOnOne;
import com.test.bidon.entity.UserEntity;
import com.test.bidon.repository.OneOnOneRepository;
import com.test.bidon.repository.UserRepository;

import java.time.LocalDate;

@Controller
public class OneOnOneController {

    @Autowired
    private OneOnOneRepository oneOnOneRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/submitQuestion")
    public String submitQuestion(
            @RequestParam("email") String email,
            @RequestParam("title") String title,
            @RequestParam("contents") String contents,
            Model model) {

        // email로 UserEntity 조회
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            // 이메일이 존재하지 않을 경우 에러 처리
            model.addAttribute("error", "등록되지 않은 이메일입니다: " + email);
            return "errorPage"; // 에러 페이지로 리다이렉트
        }

        // OneOnOne 엔터티 설정
        OneOnOne question = new OneOnOne();
        question.setUserEntityInfo(userEntity); // UserEntity 설정
        question.setTitle(title);
        question.setContents(contents);
        question.setRegdate(LocalDate.now()); // 현재 날짜 설정

        // 데이터 저장
        oneOnOneRepository.save(question);

        return "redirect:/admin/answerList"; // 성공적으로 저장 후 리다이렉트
    }
}
