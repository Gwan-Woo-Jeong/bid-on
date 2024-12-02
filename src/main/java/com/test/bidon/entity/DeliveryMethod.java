package com.test.bidon.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Table(name = "DeliveryMethod")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deliveryMethod_seq_generator")
    @SequenceGenerator(name = "deliveryMethod_seq_generator", sequenceName = "seqDeliveryMethod", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String method;

    @Column(nullable = false)
    private Integer dayCount;

    @Column(nullable = false)
    private Integer price;

}
