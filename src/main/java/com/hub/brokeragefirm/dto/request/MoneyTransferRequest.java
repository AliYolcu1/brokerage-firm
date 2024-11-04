package com.hub.brokeragefirm.dto.request;

import com.hub.brokeragefirm.enums.TransferType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MoneyTransferRequest {
    private Long customerId;
    private TransferType type;
    private BigDecimal amount;
    private String iban;
}
