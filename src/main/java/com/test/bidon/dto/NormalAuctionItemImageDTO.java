package com.test.bidon.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NormalAuctionItemImageDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "normalAuctionItemImageDTO_seq_generator")
    @SequenceGenerator(name = "normalAuctionItemImageDTO_seq_generator", sequenceName = "id", allocationSize = 1)
    private Long id;

    private String path;
}
