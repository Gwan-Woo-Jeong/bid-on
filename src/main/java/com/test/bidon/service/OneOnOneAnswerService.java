package com.test.bidon.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.bidon.dto.OneOnOneAnswerDTO;
import com.test.bidon.entity.OneOnOne;
import com.test.bidon.entity.OneOnOneAnswer;
import com.test.bidon.repository.OneOnOneAnswerRepository;
import com.test.bidon.repository.OneOnOneRepository;


@Service
public class OneOnOneAnswerService {

    @Autowired
    private OneOnOneAnswerRepository oneOnOneAnswerRepository;

    @Autowired
    private OneOnOneRepository oneOnOneRepository;

    // 답변 저장
    public void saveAnswer(Integer oneOnOneId, String answerContent) {
        Optional<OneOnOne> oneOnOneOptional = oneOnOneRepository.findById(oneOnOneId);
        if (!oneOnOneOptional.isPresent()) {
            throw new IllegalArgumentException("유효하지 않은 질문 ID입니다.");
        }

        OneOnOneAnswer answer = new OneOnOneAnswer();
        answer.setOneOnOne(oneOnOneOptional.get());
        answer.setAnswer(answerContent);

        oneOnOneAnswerRepository.save(answer);
    }

    // 특정 질문의 답변 가져오기
    public OneOnOneAnswerDTO getAnswerByQuestionId(Integer oneOnOneId) {
        Optional<OneOnOneAnswer> answerOptional = oneOnOneAnswerRepository.findByOneOnOneId(oneOnOneId);
        if (!answerOptional.isPresent()) {
            throw new IllegalArgumentException("답변이 존재하지 않습니다.");
        }

        OneOnOneAnswer answer = answerOptional.get();
        return new OneOnOneAnswerDTO(
            answer.getId(),
            answer.getOneOnOne().getId(),
            answer.getAnswer(),
            answer.getRegdate()
        );
    }
}