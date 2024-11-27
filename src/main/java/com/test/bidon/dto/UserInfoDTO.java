package com.test.bidon.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserInfoDTO {
    
	private Long id;

	//회원가입 필수값
    private String email;        
    private String password; 
    private String name;         
    
    //회원가입 선택값
    private String national; 
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;     
    private String tel;      
    private String  profile;  
    
    //서버에서 자동 설정되는 값
    private LocalDate createDate; 
    private String provider;     
    private Integer status;      
    private String userRole;     
    
    @JsonIgnore
    private MultipartFile profileFile;
    
    //기본값을 설정하는 메서드
    public void setDefaultValues() {
        this.createDate = LocalDate.now();
        this.provider = "local";
        this.status = 0;
        this.userRole = "ROLE_USER";
    }
    
}
