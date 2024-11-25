package com.test.bidon.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "UserToken")
@Getter
@Setter
@NoArgsConstructor
public class UserToken implements Serializable {

	private static final long serialVersionUID = 1L;  // 추가된 부분
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOKEN_SEQ_GENERATOR")
    @SequenceGenerator(
            name = "TOKEN_SEQ_GENERATOR",
            sequenceName = "SEQUSERTOKEN",  // 실제 DB 시퀀스 이름
            allocationSize = 1
        )
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userInfoId", nullable = false)
	private UserEntity userInfoId;
	
	@Column(name = "accessToken")
	private String accessToken;
	
	@Column(name = "refreshToken")
	private String refreshToken;
	
	@Column(name = "createTime")
	private LocalDate createTime;
	
	@Column(name = "expiryTime")
	private LocalDate expiryTime;
	
	@Column(name = "category")
	private String category;
	
	public boolean isExpired() {
        return LocalDate.now().isAfter(expiryTime);
    }
	
	
	
}
