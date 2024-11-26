package com.test.bidon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.bidon.entity.UserEntity;
import com.test.bidon.entity.UserToken;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    Optional<UserToken> findByUserInfoId(UserEntity userInfoId);  // Long -> UserEntity
    Optional<UserToken> findByAccessToken(String accessToken);
    Optional<UserToken> findByRefreshToken(String refreshToken);
    void deleteByUserInfoId(UserEntity userInfoId);  // Long -> UserEntity
	
}
