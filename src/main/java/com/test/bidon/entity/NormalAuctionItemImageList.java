package com.test.bidon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "normalAuctionItemImageId", nullable = false)
    private NormalAuctionItemImage normalAuctionItemImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "normalAuctionItemId", nullable = false)
    private NormalAuctionItem normalAuctionItem;

    @Column(nullable = false)
    private Boolean isMainImage;
}

