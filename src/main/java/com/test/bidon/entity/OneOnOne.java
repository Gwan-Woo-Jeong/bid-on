package com.test.bidon.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "OneOnOne")
@Data
public class OneOnOne {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqoneonone")
	@SequenceGenerator(name = "seqoneonone", sequenceName = "seqOneOnOne", allocationSize = 1)
	private Integer id;
	
	@Column(name = "userInfoId", nullable = false)
	private Integer userInfoId;

	@Column(nullable = false, length = 100)
	private String title;
	
	@Column(nullable = false, length = 100)
	private String contents;
	
	 // LocalDate로 변경
    @Column(nullable = false)
    private LocalDate regdate;

    // regdate Setter
    public void setRegdate(String regdate) {
        this.regdate = LocalDate.parse(regdate); // String 값을 LocalDate로 변환
    }

    // regdate Getter
    public LocalDate getRegdate() {
        return regdate;
    }
	
	
}
