package com.test.bidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.bidon.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);

	boolean existsByEmail(String email);
	
	
	
}
