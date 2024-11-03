package com.hub.brokeragefirm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "assets")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private String assetName;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal size;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal usableSize;

}
