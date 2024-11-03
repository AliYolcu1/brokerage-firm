package com.hub.brokeragefirm.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawRequest {
    private Long customerId;
    private BigDecimal amount;
    private String iban;
}
