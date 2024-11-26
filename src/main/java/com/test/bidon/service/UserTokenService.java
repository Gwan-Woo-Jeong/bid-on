package com.test.bidon.service;

import java.time.LocalDate;
//LocalDateTime을 사용하는 경우 추가
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.bidon.entity.UserEntity;
import com.test.bidon.entity.UserToken;
import com.test.bidon.repository.UserRepository;
import com.test.bidon.repository.UserTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional  // 트랜잭션 추가
public class UserTokenService {

    private final UserTokenRepository userTokenRepository;
    private final UserRepository userRepository;

    public UserToken createToken(Long userId, String accessToken, String refreshToken,
                               String category, LocalDate expiryTime) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId));

        // 기존 토큰이 있다면 삭제 (선택사항)
        userTokenRepository.findByUserInfoId(user)
                .ifPresent(existingToken -> userTokenRepository.delete(existingToken));

        UserToken token = new UserToken();
        token.setUserInfoId(user);
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setCategory(category);
        token.setExpiryTime(expiryTime);
        token.setCreateTime(LocalDate.now());

        return userTokenRepository.save(token);
    }

    @Transactional(readOnly = true)  // 읽기 전용 트랜잭션
    public Optional<UserToken> findByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId));
        return userTokenRepository.findByUserInfoId(user);
    }

    public void deleteToken(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId));
        userTokenRepository.deleteByUserInfoId(user);
    }

    @Transactional(readOnly = true)
    public boolean isTokenValid(String accessToken) {
        return userTokenRepository.findByAccessToken(accessToken)
                .map(token -> !token.isExpired())  // isExpired() 메소드 활용
                .orElse(false);
    }

    // 추가 유틸리티 메서드들
    @Transactional(readOnly = true)
    public Optional<UserToken> findByAccessToken(String accessToken) {
        return userTokenRepository.findByAccessToken(accessToken);
    }

    @Transactional(readOnly = true)
    public Optional<UserToken> findByRefreshToken(String refreshToken) {
        return userTokenRepository.findByRefreshToken(refreshToken);
    }

    // 토큰 갱신 메서드
    public UserToken refreshToken(String refreshToken, LocalDate newExpiryTime) {
        UserToken token = userTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다."));

        if (token.isExpired()) {
            throw new IllegalArgumentException("만료된 토큰입니다.");
        }

        token.setExpiryTime(newExpiryTime);
        return userTokenRepository.save(token);
    }
}