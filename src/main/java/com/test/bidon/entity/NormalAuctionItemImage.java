package com.test.bidon.entity;

import com.test.bidon.dto.NormalAuctionItemImageDTO;
import jakarta.persistence.*;
import lombok.*;

// 이미지 각각의 시퀀스와 이미지 파일의 경로가 들어가 있는 Entity
@Entity
@Getter
@ToString
@Table(name = "NormalAuctionItemImage")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NormalAuctionItemImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "normalAuctionItemImage_seq_generator")
    @SequenceGenerator(name = "normalAuctionItemImage_seq_generator", sequenceName = "id", allocationSize = 1)
    private Long id;

    private String path;

    // Entity 본인을 DTO로 변환시키는 method
    public static NormalAuctionItemImageDTO toDTO(NormalAuctionItemImage image) {

        return NormalAuctionItemImageDTO.builder()
                .id(image.id)
                .path(image.path)
                .build();
    }

    // DTO로부터 값을 받지 않고 본인 스스로를 DTO로 변환시키는 method
    public NormalAuctionItemImageDTO toDTO() {
        return NormalAuctionItemImageDTO.builder()
                .id(this.id)
                .path(this.path)
                .build();
    }
}
