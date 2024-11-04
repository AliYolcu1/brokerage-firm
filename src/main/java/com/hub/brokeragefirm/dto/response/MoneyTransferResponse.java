package com.hub.brokeragefirm.dto.response;

import com.hub.brokeragefirm.enums.TransferType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyTransferResponse {
    private Long transactionId;
    private Long customerId;
    private TransferType type; // "DEPOSIT" or "WITHDRAW"
    private BigDecimal amount;
    private BigDecimal balanceAfterTransaction;
    private LocalDateTime transactionDate;
    private String iban;
}
