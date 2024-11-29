package com.test.bidon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "NormalAuctionItemImageList")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NormalAuctionItemImageList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "normalAuctionItemImageId", nullable = false)
    private NormalAuctionItemImage normalAuctionItemImage;

    @ManyToOne
    @JoinColumn(name = "normalAuctionItemId", nullable = false)
    private NormalAuctionItem normalAuctionItem;

    @Column(nullable = false)
    private Integer isMainImage;
}

