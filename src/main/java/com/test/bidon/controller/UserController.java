package com.test.bidon.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.test.bidon.config.security.CustomUserDetails;
import com.test.bidon.dto.UserInfoDTO;
import com.test.bidon.entity.UserEntity;
import com.test.bidon.repository.UserRepository;
import com.test.bidon.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final String uploadPath = "C:/temp/uploads"; // 실제 파일이 저장될 경로

    private final UserRepository userRepository;
    
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userInfoDTO", new UserInfoDTO());
        return "user/signup";
    }

    @PostMapping("/signok")
    @ResponseBody
    public ResponseEntity<Map<String, String>> signupok(
            @RequestPart(name = "userInfoDTO") UserInfoDTO userInfoDTO,
            @RequestPart(name = "profileFile", required = false) MultipartFile profileFile) {
        try {
            log.info("회원가입 요청 데이터: {}", userInfoDTO);

            userInfoDTO.setDefaultValues();

            if (userService.isEmailExists(userInfoDTO.getEmail())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "이미 사용 중인 이메일입니다."));
            }

            if (profileFile != null && !profileFile.isEmpty()) {
                try {
                    String savedFileName = saveProfileFile(profileFile);
                    userInfoDTO.setProfile(savedFileName);
                } catch (IOException e) {
                    log.error("프로필 이미지 저장 중 오류: ", e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(Map.of("message", "프로필 이미지 저장 중 오류가 발생했습니다."));
                }
            } else {
                userInfoDTO.setProfile("default.jpg");
            }

            UserEntity savedUser = userService.registerUser(userInfoDTO);

            return ResponseEntity.ok(Map.of("message", "회원가입이 완료되었습니다."));
        } catch (Exception e) {
            log.error("회원가입 처리 중 오류: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "회원가입 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    private String saveProfileFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "default.jpg";
        }

        // uploads 디렉토리 생성
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String savedFileName = UUID.randomUUID().toString() + fileExtension;

        File destFile = new File(uploadPath + File.separator + savedFileName);
        file.transferTo(destFile);

        return savedFileName;
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "user/login";
    }
    
    @GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);	//로그아웃
		}
		
		return "redirect:/";
	}

    

    @GetMapping("/mypage")
    @PreAuthorize("isAuthenticated()")
    public String mypage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
            
            UserEntity user = userRepository.findById(customUserDetails.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            // CustomUserDetails 대신 DB에서 가져온 최신 user 정보를 사용
            model.addAttribute("name", user.getName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("national", user.getNational());
            model.addAttribute("birth", user.getBirth());
            model.addAttribute("tel", user.getTel());
            model.addAttribute("createDate", user.getCreateDate());
            model.addAttribute("profile", user.getProfile());
            
            log.info("User info found - Name: {}, Email: {}", user.getName(), user.getEmail());
        } else {
            log.warn("User information not found");
        }

        return "user/mypage";
    }
    
    @PostMapping("/api/user/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUser(	//value때문에 경고뜨는데, 이거 삭제하면 에러남
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "password", required = false) String password,
        @RequestParam(value = "birth", required = false) String birth,
        @RequestParam(value = "national", required = false) String national,
        @RequestParam(value = "tel", required = false) String tel,
        @RequestParam(value = "profile", required = false) MultipartFile profile) {
        
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            
            UserInfoDTO updateDto = new UserInfoDTO();
            updateDto.setName(name);
            updateDto.setPassword(password);
            if (birth != null && !birth.isEmpty()) {
                updateDto.setBirth(LocalDate.parse(birth));
            }
            updateDto.setNational(national);
            updateDto.setTel(tel);
            
            // 파일이 있는 경우만 처리
            if (profile != null && !profile.isEmpty()) {
                String fileName = StringUtils.cleanPath(profile.getOriginalFilename());
                updateDto.setProfile(fileName);
            }
            
            UserEntity updatedUser = userService.updateUser(userDetails.getId(), updateDto);
            
            return ResponseEntity.ok()
                .body(Map.of(
                    "success", true,
                    "message", "정보가 성공적으로 수정되었습니다."
                ));
                
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of(
                    "success", false,
                    "message", e.getMessage()
                ));
        }
    }
	 
}