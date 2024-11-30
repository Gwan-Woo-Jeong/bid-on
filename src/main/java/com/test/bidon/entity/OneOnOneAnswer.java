package com.test.bidon.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "OneOnOneAnswer")
public class OneOnOneAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqoneononeanswer")
	@SequenceGenerator(name = "seqoneononeanswer", sequenceName = "seqOneOnOneAnswer", allocationSize = 1)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OneOnOneId", nullable = false)
	private OneOnOne oneonone;
	
	@Column(nullable = false)
	private String answer;
	
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime regdate;
	
}
