package com.hub.brokeragefirm.service;

import com.hub.brokeragefirm.dto.request.MoneyTransferRequest;
import com.hub.brokeragefirm.dto.response.AssetResponse;
import com.hub.brokeragefirm.dto.response.MoneyTransferResponse;

import java.util.List;

public interface AssetService {
    List<AssetResponse> listAssets(Long customerId);

    MoneyTransferResponse deposit(MoneyTransferRequest request);

    MoneyTransferResponse withdraw(MoneyTransferRequest request);
}
