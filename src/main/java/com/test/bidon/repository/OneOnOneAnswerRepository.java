package com.test.bidon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.bidon.entity.OneOnOneAnswer;
@Repository
public interface OneOnOneAnswerRepository extends JpaRepository<OneOnOneAnswer, Integer> {

    // 특정 질문 ID로 답변 가져오기
    Optional<OneOnOneAnswer> findByOneOnOneId(Integer oneOnOneId);
}


