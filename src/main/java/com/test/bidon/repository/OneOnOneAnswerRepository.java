package com.test.bidon.repository;

import com.test.bidon.entity.OneOnOneAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OneOnOneAnswerRepository extends JpaRepository<OneOnOneAnswer, Integer> {
    // 모든 답변 리스트를 가져오기 (JpaRepository의 findAll() 활용)
    default List<OneOnOneAnswer> getAllAnswers() {
        return findAll(); // JpaRepository 기본 메서드 호출
    }

    // 새로운 답변 저장 (JpaRepository의 save() 활용)
    default void saveAnswer(OneOnOneAnswer answer) {
        save(answer); // JpaRepository 기본 메서드 호출
    }
}
