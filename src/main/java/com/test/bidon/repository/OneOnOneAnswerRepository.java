package com.test.bidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.bidon.entity.OneOnOne;
import com.test.bidon.entity.OneOnOneAnswer;

public interface OneOnOneAnswerRepository extends JpaRepository<OneOnOneAnswer, Long> {

	OneOnOneAnswer findByOneonone(OneOnOne oneonone);

}
