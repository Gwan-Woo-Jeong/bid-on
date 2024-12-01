package com.test.bidon.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.test.bidon.dto.CustomOAuth2User;
import com.test.bidon.dto.NormalAuctionItemDTO;
import com.test.bidon.dto.UserInfoDTO;
import com.test.bidon.entity.NormalAuctionItem;
import com.test.bidon.entity.OneOnOne;
import com.test.bidon.entity.OneOnOneAnswer;
import com.test.bidon.entity.UserEntity;
import com.test.bidon.repository.NormalAuctionItemRepository;
import com.test.bidon.repository.OneOnOneAnswerRepository;
import com.test.bidon.repository.OneOnOneRepository;
import com.test.bidon.repository.UserRepository;
import com.test.bidon.service.BidOrderService;
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
    private final UserRepository userRepository;
    private final OneOnOneRepository oneOnOneRepository;
    private final OneOnOneAnswerRepository oneOnOneAnswerRepository;
    private final NormalAuctionItemRepository normalAuctionItemRepository;
    private final BidOrderService bidOrderService;
    
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
                userInfoDTO.setProfile("default-profile.svg");
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
            return "default-profile.svg";
        }

        String uploadDir = "src/main/resources/static/uploads/profiles/";
        
        // 디렉토리가 없으면 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // 파일 저장
        Path path = Paths.get(uploadDir + fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
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
        UserEntity user = null;
        
        if (auth.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
            user = userRepository.findById(customUserDetails.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        } else if (auth.getPrincipal() instanceof CustomOAuth2User) {
            CustomOAuth2User oauth2User = (CustomOAuth2User) auth.getPrincipal();
            user = userRepository.findByEmail(oauth2User.getEmail());
        }

        if (user != null) {
        
            //CustomUserDetails 대신 DB에서 가져온 최신 user 정보를 사용
            model.addAttribute("name", user.getName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("national", user.getNational());
            model.addAttribute("birth", user.getBirth());
            model.addAttribute("tel", user.getTel());
            model.addAttribute("createDate", user.getCreateDate());
            model.addAttribute("profile", user.getProfile());
            model.addAttribute("provider", user.getProvider());
            
            //관리자 문의
            //OneOnOne 데이터 가져오기
            List<OneOnOne> oneOnOneList = oneOnOneRepository.findByUserEntityInfo(user);
            
            //각 OneOnOne에 대한 답변을 가져와서 설정
            for (OneOnOne oneOnOne : oneOnOneList) {
                OneOnOneAnswer answer = oneOnOneAnswerRepository.findByOneonone(oneOnOne);
                if (answer != null) {
                    oneOnOne.setOneOnOneAnswer(answer);
                }
            }
            
            model.addAttribute("oneOnOneList", oneOnOneList);

            //나의 활동
            List<NormalAuctionItem> items = normalAuctionItemRepository.findByUserInfoId(user.getId());
            List<NormalAuctionItemDTO> normalAuctionItemList = items.stream()
            
            		.map(item -> {
                        // BidOrderService를 통해 최종 가격 조회
                        Integer finalPrice = bidOrderService.getFinalPriceByNormalBidId(item.getId());
                        
                        return NormalAuctionItemDTO.builder()
                            .id(item.getId())
                            .categorySubId(item.getCategorySubId())
                            .userInfoId(item.getUserInfoId())
                            .name(item.getName())
                            .description(item.getDescription())
                            .startTime(item.getStartTime())
                            .endTime(item.getEndTime())
                            .startPrice(item.getStartPrice())
                            .status(item.getStatus())
                            //.finalPrice(finalPrice)  // BidOrderService에서 가져온 최종 가격
                            .finalPrice(bidOrderService.getFinalPriceByNormalBidId(item.getId()))
                            .buyerName(bidOrderService.getBuyerNameByNormalBidId(item.getId()))
                            .build();
                    })
                    .collect(Collectors.toList());
            		
            model.addAttribute("normalAuctionItemList", normalAuctionItemList);
            
            
            log.info("User info found - Name: {}, Email: {}, Provider: {}", user.getName(), user.getEmail(), user.getProvider());
            log.info("OneOnOne count: {}", oneOnOneList.size());
        } else {
            log.warn("User information not found");
        }

        return "user/mypage";
    }
    
    
    @PostMapping("/api/user/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUser(
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "password", required = false) String password,
        @RequestParam(value = "birth", required = false) String birth,
        @RequestParam(value = "national", required = false) String national,
        @RequestParam(value = "tel", required = false) String tel,
        @RequestParam(value = "profile", required = false) MultipartFile profile) {
        
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserEntity user = null;
            
            if (auth.getPrincipal() instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
                user = userRepository.findById(userDetails.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            } else if (auth.getPrincipal() instanceof CustomOAuth2User) {
                CustomOAuth2User oauth2User = (CustomOAuth2User) auth.getPrincipal();
                user = userRepository.findByEmail(oauth2User.getEmail());
            }
            
            if (user == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of(
                        "success", false,
                        "message", "사용자를 찾을 수 없습니다."
                    ));
            }

            // OAuth2 사용자의 경우 비밀번호 수정 방지
            if ("google".equals(user.getProvider()) && password != null) {
                return ResponseEntity.badRequest()
                    .body(Map.of(
                        "success", false,
                        "message", "소셜 로그인 사용자는 비밀번호를 변경할 수 없습니다."
                    ));
            }
            
            UserInfoDTO updateDto = new UserInfoDTO();
            updateDto.setName(name);
            updateDto.setPassword(password);
            if (birth != null && !birth.isEmpty()) {
                updateDto.setBirth(LocalDate.parse(birth));
            }
            updateDto.setNational(national);
            updateDto.setTel(tel);
            
            if (profile != null && !profile.isEmpty()) {
                String savedFileName = saveProfileFile(profile);
                updateDto.setProfile(savedFileName);
            }
            
            UserEntity updatedUser = userService.updateUser(user.getId(), updateDto);
            
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