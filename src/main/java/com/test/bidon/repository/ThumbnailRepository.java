package com.test.bidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.bidon.entity.Thumbnail;

@Repository
public interface ThumbnailRepository extends JpaRepository<Thumbnail, Integer> {
	
	
	
	
}

