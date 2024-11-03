package com.hub.brokeragefirm.entity;

import com.hub.brokeragefirm.enums.TransferStatus;
import com.hub.brokeragefirm.enums.TransferType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "money_transfers")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoneyTransfers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransferType type;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime transferDate;

    @Column
    private String iban;  // For withdrawals

    @Column(nullable = false)
    private Long processedByEmployeeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransferStatus status;

}
