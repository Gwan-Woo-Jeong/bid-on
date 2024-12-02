package com.test.bidon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WinningBid")
public class WinningBid {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "winningBid_seq_generator")
    @SequenceGenerator(name = "winningBid_seq_generator", sequenceName = "seqWinningBid", allocationSize = 1)
    private Long id;

    @Column(name = "userInfoId", nullable = false)
    private Long userInfoId;

    @Column(name = "liveBidId")
    private Long liveBidId;

    @Column(name = "normalBidId")
    private Long normalBidId;

    @ManyToOne
    @JoinColumn(name = "liveBidId", insertable = false, updatable = false)
    private LiveBidCost liveBidCost;

    // toString 메서드
    @Override
    public String toString() {
        return "WinningBid{" +
                "id=" + id +
                ", userInfoId=" + userInfoId +
                ", liveBidId=" + liveBidId +
                ", normalBidId=" + normalBidId +
                '}';
    }
}
