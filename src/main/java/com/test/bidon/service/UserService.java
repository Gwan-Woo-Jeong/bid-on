package com.test.bidon.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.bidon.dto.UserInfoDTO;
import com.test.bidon.entity.UserEntity;
import com.test.bidon.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional  // 추가
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 기존 메서드 유지
    public void signup(UserInfoDTO dto) {
        //이메일 중복검사
        if(isEmailExists(dto.getEmail())) {
            System.out.println("이미 사용중인 이메일입니다.");
            return;
        }

        //DTO > (변환) > Entity
        UserEntity user = UserEntity.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build();

        userRepository.save(user);
    }

    // 이메일 존재 여부 확인 메서드 추출
    public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // 회원 등록 메서드 (새로운 버전)
    public UserEntity registerUser(UserInfoDTO dto) {
        UserEntity user = UserEntity.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .tel(dto.getTel())
                .birth(dto.getBirth())
                .national(dto.getNational())
                .createDate(java.time.LocalDate.now())
                .status(0)  // 활성 상태
                .userRole("ROLE_MEMBER")  // 기본 권한
                .provider("local")  // 일반 회원가입
                .build();

        return userRepository.save(user);
    }

    // 사용자 조회 메서드 (필요시 사용)
    @Transactional(readOnly = true)
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}