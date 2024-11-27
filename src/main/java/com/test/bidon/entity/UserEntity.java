package com.test.bidon.entity;

import java.time.LocalDate;

import com.test.bidon.dto.UserInfoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "UserInfo")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
    @SequenceGenerator(
            name = "USER_SEQ_GENERATOR",
            sequenceName = "SEQUSERINFO",  // 실제 DB 시퀀스 이름
            allocationSize = 1
        )
	private Long id;			//사용자ID
	
	@Column(nullable = false)
	private String email;		//이메일
	
	// 소셜 로그인 사용자는 비밀번호가 없을 수 있음
	@Column(nullable = false)
	private String password;	//비밀번호
	
	@Column(nullable = false)
	private String name;		//이름
	
	@Column(nullable = true)
	private String national;	//국가
	
	@Column(nullable = true)
	private LocalDate birth;  	//생년월일
	
	@Column(nullable = true)
	private String tel;  		// 전화번호

	private String profile;  	// 프로필 (optional)

	@Column(nullable = false)
	private LocalDate createDate;//계정 생성일
	
	@Column(nullable = false)
    private String provider;    //"local" 또는 "google"
	
	@Column(nullable = false)
	private Integer status;  	//상태(예: 0: 활성, 1: 비활성)

	@Column(nullable = false)
	@Builder.Default
	private String userRole = "ROLE_USER";  	//사용자 역할(예: ROLE_USER, ROLE_ADMIN)

	public UserInfoDTO toDTO() {
		return UserInfoDTO.builder()
				.id(this.getId())
				.email(this.getEmail())
				.password(this.getPassword())
				.name(this.getName())
				.national(this.getNational())
				.birth(this.getBirth())
				.tel(this.getTel())
				.profile(this.getProfile())
				.createDate(this.getCreateDate())
				.provider(this.getProvider())
				.status(this.getStatus())
				.userRole(this.getUserRole())
				.build();
	}
	
}

















