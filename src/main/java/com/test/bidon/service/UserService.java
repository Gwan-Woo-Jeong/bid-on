package com.test.bidon.service;

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

    //이메일 존재 여부 확인 메서드 추출
    public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    //회원가입에서 입력한 정보 저장하는 메서드
    public UserEntity registerUser(UserInfoDTO dto) {
        if(isEmailExists(dto.getEmail())) {
            throw new RuntimeException("이미 사용중인 이메일입니다.");
        }

        UserEntity entity = UserEntity.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .tel(dto.getTel())
                .birth(dto.getBirth())
                .national(dto.getNational())
                .createDate(java.time.LocalDate.now())
                .status(0)
                .userRole("ROLE_USER")
                .provider("local")
                .build();
                
        // 저장 전에 모든 필드값 출력 (디버깅용)
        System.out.println("=== 저장할 엔티티 정보 ===");
        System.out.println("email: " + entity.getEmail());
        System.out.println("password: " + entity.getPassword());
        System.out.println("name: " + entity.getName());
        System.out.println("national: " + entity.getNational());
        System.out.println("birth: " + entity.getBirth());
        System.out.println("tel: " + entity.getTel());
        System.out.println("profile: " + entity.getProfile());
        System.out.println("createDate: " + entity.getCreateDate());
        System.out.println("status: " + entity.getStatus());
        System.out.println("userRole: " + entity.getUserRole());
        System.out.println("provider: " + entity.getProvider());
        System.out.println("=========================");

        try {
            UserEntity savedEntity = userRepository.save(entity);
            System.out.println("새로운 사용자 저장 성공: " + entity.getEmail());
            return savedEntity;
        } catch (Exception e) {
            System.out.println("사용자 저장 실패: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("사용자 저장 중 오류 발생", e);
        }
    }

    // 사용자 조회 메서드 (필요시 사용)
    @Transactional(readOnly = true)
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}