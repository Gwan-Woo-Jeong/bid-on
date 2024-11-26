package com.test.bidon.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.test.bidon.dto.UserInfoDTO;
import com.test.bidon.entity.UserEntity;
import com.test.bidon.service.UserService;

import org.springframework.validation.annotation.Validated;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;

	@GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userInfoDTO", new UserInfoDTO());
        return "user/signup";
    }

	@PostMapping("/signok")
    public String signupok(@Validated UserInfoDTO dto, 
                          BindingResult bindingResult, 
                          Model model) {
        // 유효성 검사 실패 시
        if (bindingResult.hasErrors()) {
            return "user/signup";
        }

        // 이메일 중복 체크
        if (userService.isEmailExists(dto.getEmail())) {
            model.addAttribute("errorMessage", "이미 사용중인 이메일입니다.");
            return "user/signup";
        }

        try {
            userService.registerUser(dto);
            model.addAttribute("successMessage", "회원가입이 완료되었습니다.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "회원가입 중 오류가 발생했습니다: " + e.getMessage());
            return "user/signup";
        }
    }
	
	@GetMapping("/login")
	public String login(Model model) {
		return "user/login";
	}

	 @GetMapping("/mypage")
	 @PreAuthorize("isAuthenticated()")  // 로그인한 사용자만 접근 가능
	 public String mypage(Model model, @AuthenticationPrincipal UserEntity user) {
	        model.addAttribute("user", user);
	        return "user/mypage";
	    }
}
