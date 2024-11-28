package com.test.bidon.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.test.bidon.config.security.CustomUserDetails;
import com.test.bidon.dto.UserInfoDTO;
import com.test.bidon.entity.UserEntity;
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
            // CustomUserDetails에서 UserEntity 정보를 가져오는 방법에 따라:
            // 예: customUserDetails.getUser() 또는 직접 정보 접근
            model.addAttribute("name", customUserDetails.getName());
            model.addAttribute("email", customUserDetails.getUsername());
            model.addAttribute("national", customUserDetails.getNational());
            model.addAttribute("birth", customUserDetails.getBirth());
            model.addAttribute("tel", customUserDetails.getTel());
            model.addAttribute("createDate", customUserDetails.getCreateDate());
            model.addAttribute("profile", customUserDetails.getProfile());
            log.info("User info found - Name: {}, Email: {}", customUserDetails.getName(), customUserDetails.getUsername(), customUserDetails.getNational());
        } else {
            log.warn("User information not found");
        }
	  
    	return "user/mypage"; 
	}
	 
}