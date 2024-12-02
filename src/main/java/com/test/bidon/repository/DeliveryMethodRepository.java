package com.test.bidon.repository;


import com.test.bidon.entity.DeliveryMethod;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethod, Long> {
	@NonNull
	List<DeliveryMethod> findAll();
	DeliveryMethod findById(long id);
}
