package com.test.bidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.bidon.entity.OneOnOne;
import com.test.bidon.repository.OneOnOneRepository;

import java.time.LocalDate;

@Controller
public class OneOneOneController {

    @Autowired
    private OneOnOneRepository oneOnOneRepository;

    @PostMapping("/submitQuestion")
    public String submitQuestion(
            @RequestParam("userInfo") Integer userInfo,
            @RequestParam("title") String title,
            @RequestParam("contents") String contents,
            @RequestParam(value = "regdate", required = false) String regdate,
            Model model) {

        OneOnOne question = new OneOnOne();

        // 기본값으로 현재 날짜를 설정
        if (regdate == null || regdate.isEmpty()) {
            regdate = LocalDate.now().toString(); // 현재 날짜를 yyyy-MM-dd 형식으로 설정
        }

        question.setUserInfoId(userInfo);
        question.setTitle(title);
        question.setContents(contents);
        

        // 데이터 저장
        oneOnOneRepository.save(question);

        return "redirect:/admin/OneOnOne"; // URL 오타 수정
    }
}
