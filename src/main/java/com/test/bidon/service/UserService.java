package com.test.bidon.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.bidon.dto.UserInfoDTO;
import com.test.bidon.entity.UserEntity;
import com.test.bidon.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional  // 추가
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //이메일 존재 여부 확인 메서드 추출
    public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    //회원가입에서 입력한 정보 저장하는 메서드
    public UserEntity registerUser(UserInfoDTO dto) {
    	
    	log.info("회원가입 서비스 시작: {}", dto);
    	
        if(isEmailExists(dto.getEmail())) {
            throw new RuntimeException("이미 사용중인 이메일입니다.");
        }

        UserEntity entity = UserEntity.builder()
        		.profile(dto.getProfile())
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .tel(dto.getTel())
                .birth(dto.getBirth())
                .national(dto.getNational())
                .profile(dto.getProfile())
                .createDate(java.time.LocalDate.now())
                .status(0)
                .userRole("ROLE_USER")
                .provider("local")
                .build();
                
        // 저장 전에 모든 필드값 출력 (디버깅용)
        logUserEntityDetails(entity);

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

    private void logUserEntityDetails(UserEntity userEntity) {
        log.debug("=== 저장할 엔티티 정보 ===");
        log.debug("email: {}", userEntity.getEmail());
        log.debug("password: {}", userEntity.getPassword());
        log.debug("name: {}", userEntity.getName());
        log.debug("national: {}", userEntity.getNational());
        log.debug("birth: {}", userEntity.getBirth());
        log.debug("tel: {}", userEntity.getTel());
        log.debug("profile: {}", userEntity.getProfile());
        log.debug("createDate: {}", userEntity.getCreateDate());
        log.debug("status: {}", userEntity.getStatus());
        log.debug("userRole: {}", userEntity.getUserRole());
        log.debug("provider: {}", userEntity.getProvider());
        log.debug("=========================");
    }
    
    
    @Transactional
    public UserEntity updateUser(Long id, UserInfoDTO updateDto) {
        UserEntity user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (updateDto.getPassword() != null && !updateDto.getPassword().isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(updateDto.getPassword()));
        }
        if (updateDto.getName() != null) user.setName(updateDto.getName());
        if (updateDto.getBirth() != null) user.setBirth(updateDto.getBirth());
        if (updateDto.getNational() != null) user.setNational(updateDto.getNational());
        if (updateDto.getTel() != null) user.setTel(updateDto.getTel());
        if (updateDto.getProfile() != null) user.setProfile(updateDto.getProfile());
        
        return userRepository.save(user);
    }
    
    
    
}












