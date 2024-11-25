package com.test.bidon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.test.bidon.dto.UserInfoDTO;
import com.test.bidon.service.UserService;

import org.springframework.validation.annotation.Validated;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;

	@GetMapping("/signup")
    public String signup(UserInfoDTO dto) {
        return "user/signup";
    }

	@PostMapping("/signok")
	public String signupok(UserInfoDTO dto) {
	    // 이메일 중복 체크
	    if (userService.isEmailExists(dto.getEmail())) {
	        System.out.println("이미 사용중인 이메일입니다: " + dto.getEmail());
	        return "user/signup";    // 회원가입 페이지로 다시 이동
	    }

	    try {
	        userService.registerUser(dto);
	        return "redirect:/login";    // 회원가입 성공 시 로그인 페이지로
	    } catch (Exception e) {
	        System.out.println("회원가입 중 오류 발생: " + e.getMessage());
	        return "user/signup";    // 에러 발생 시 회원가입 페이지로
	    }
	}

    @GetMapping("/my")
    public String my() {
        return "user/my";
    }
}
