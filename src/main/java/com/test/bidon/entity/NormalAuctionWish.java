package com.test.bidon.entity;



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
import lombok.ToString;

@Entity
@Getter
@ToString
@Table(name = "NormalAuctionWish")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NormalAuctionWish {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "normalAuctionItem_seq_generator")
    @SequenceGenerator(name = "normalAuctionItem_seq_generator", sequenceName = "seqNormalAuctionItem", allocationSize = 1)
    private Long id;
	
	@Column(nullable = false, insertable = false, updatable = false)
	private Long userInfoId;
	
	@Column(nullable = false, insertable = false, updatable = false)
	private Long normalAuctionItemId;

}
