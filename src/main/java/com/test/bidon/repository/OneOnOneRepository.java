package com.test.bidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.bidon.entity.OneOnOne;

@Repository
public interface OneOnOneRepository extends JpaRepository<OneOnOne, Integer> {

	

}
