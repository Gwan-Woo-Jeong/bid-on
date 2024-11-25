package com.test.bidon.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserInfoDTO {
    
    private Long id;
    private String email;        
    private String password;     
    private String name;         
    private String national;     
    private LocalDate birth;     
    private String tel;          
    private String profile;      
    private LocalDate createDate; 
    private String provider;     
    private Integer status;      
    private String userRole;     
}
