package com.test.bidon.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.bidon.entity.OneOnOne;
import com.test.bidon.entity.UserEntity;
import com.test.bidon.repository.OneOnOneRepository;
import com.test.bidon.repository.UserRepository;


@RestController
@RequestMapping("/user")
public class OneOnOneController {

    @Autowired
    private OneOnOneRepository oneOnOneRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/submit")
    public Map<String, Object> submitQuestion(
            @RequestParam("email") String email,
            @RequestParam("title") String title,
            @RequestParam("contents") String contents) {
        Map<String, Object> response = new HashMap<>();

        try {
            // 사용자 조회
            UserEntity userEntity = userRepository.findByEmail(email);
            if (userEntity == null) {
                throw new IllegalArgumentException("등록되지 않은 이메일입니다.");
            }

            // 질문 저장
            OneOnOne question = new OneOnOne();
            question.setUserEntityInfo(userEntity);
            question.setTitle(title);
            question.setContents(contents);
            question.setRegdate(LocalDate.now());
            oneOnOneRepository.save(question);

            // 성공 메시지 반환
            response.put("success", true);
            response.put("message", "질문이 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            // 실패 메시지 반환
            response.put("success", false);
            response.put("message", "질문 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
        return response;
    }
    
    

}
