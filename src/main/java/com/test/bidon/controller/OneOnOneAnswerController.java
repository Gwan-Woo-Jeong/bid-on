package com.test.bidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.bidon.dto.OneOnOneAnswerDTO;
import com.test.bidon.service.OneOnOneAnswerService;

@RestController
@RequestMapping("/admin/community/oneOnOneAnswer")
public class OneOnOneAnswerController {

    @Autowired
    private OneOnOneAnswerService oneOnOneAnswerService;

    // 답변 저장
    @PostMapping("/save")
    public ResponseEntity<String> saveAnswer(@RequestParam Integer oneOnOneId, @RequestParam String answer) {
        oneOnOneAnswerService.saveAnswer(oneOnOneId, answer);
        return ResponseEntity.ok("답변이 저장되었습니다.");
    }

    // 특정 질문의 답변 조회
    @GetMapping("/{oneOnOneId}")
    public ResponseEntity<OneOnOneAnswerDTO> getAnswer(@PathVariable Integer oneOnOneId) {
        OneOnOneAnswerDTO answer = oneOnOneAnswerService.getAnswerByQuestionId(oneOnOneId);
        return ResponseEntity.ok(answer);
    }
}
