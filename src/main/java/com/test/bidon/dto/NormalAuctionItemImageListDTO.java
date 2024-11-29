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
public class NormalAuctionItemImageListDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "normalAuctionItemImageListDTO_seq_generator")
    @SequenceGenerator(name = "normalAuctionItemImageListDTO_seq_generator", sequenceName = "id", allocationSize = 1)
    private Long id;

    private Long normalAuctionItemImageId;
    private Long normalAuctionItemId;
    private Long isMainImage;
}
