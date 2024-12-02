package com.test.bidon.entity;

import com.test.bidon.dto.LiveAuctionItemImageDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "LiveAuctionItemImage")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveAuctionItemImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "liveAuctionItemImage_seq_generator")
    @SequenceGenerator(name = "liveAuctionItemImage_seq_generator", sequenceName = "seqLiveAuctionItemImage", allocationSize = 1)
    private Long id;

    private String path;

    public LiveAuctionItemImageDTO toDTO() {
        return LiveAuctionItemImageDTO.builder()
                .id(this.getId())
                .path(this.getPath())
                .build();
    }

}













