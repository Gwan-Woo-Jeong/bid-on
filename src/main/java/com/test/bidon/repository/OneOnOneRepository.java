package com.test.bidon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.bidon.entity.OneOnOne;
import com.test.bidon.entity.UserEntity;

@Repository
public interface OneOnOneRepository extends JpaRepository<OneOnOne, Integer> {

	List<OneOnOne> findByUserEntityInfo(UserEntity user);	//종민아 이거 내가 넣었는데 혹시 에러나면 알려줘 -혜미-

	

}
