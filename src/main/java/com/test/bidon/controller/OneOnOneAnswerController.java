package com.test.bidon.controller;

import com.test.bidon.entity.OneOnOne;
import com.test.bidon.entity.OneOnOneAnswer;
import com.test.bidon.repository.OneOnOneAnswerRepository;

import com.test.bidon.repository.OneOnOneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/answerList")
public class OneOnOneAnswerController {

    @Autowired
    private OneOnOneAnswerRepository oneOnOneAnswerRepository;

    @Autowired
    private OneOnOneRepository oneOnOneRepository; // 질문 리포지토리 추가

    // GET 요청: 답변 리스트 및 답변 작성 폼 표시
    @GetMapping
    public String showAnswerList(@RequestParam(name = "seqOneOnOne", required = false) Integer seqOneOnOne, Model model) {
        if (seqOneOnOne != null) {
            // 특정 질문에 대한 답변 작성 폼을 표시
            model.addAttribute("seqOneOnOne", seqOneOnOne);
            Optional<OneOnOneAnswer> answers = oneOnOneAnswerRepository.findById(seqOneOnOne);
            model.addAttribute("answer", new OneOnOneAnswer());
        } else {
            // 모든 답변 리스트 표시
            List<OneOnOneAnswer> answers = oneOnOneAnswerRepository.findAll(); // findAll() 사용
            model.addAttribute("answers", answers);
        }
        return "admin/answerList"; // answerList.html로 이동
    }

    // POST 요청: 답변 저장
    @PostMapping
    public String saveAnswer(@ModelAttribute OneOnOneAnswer answer, @RequestParam Integer seqOneOnOne) {
        // 외래 키로 참조할 질문 엔티티를 데이터베이스에서 조회
        OneOnOne question = oneOnOneRepository.findById(seqOneOnOne)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID: " + seqOneOnOne));

        // 답변에 질문 엔티티 설정
        answer.setSeqOneOnOne(question);

        // 답변 저장
        oneOnOneAnswerRepository.save(answer);

        // 저장 후 답변 리스트로 리다이렉트
        return "redirect:/admin/answerList";
    }
}
