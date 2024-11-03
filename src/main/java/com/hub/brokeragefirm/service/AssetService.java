package com.hub.brokeragefirm.service;

import com.hub.brokeragefirm.dto.request.DepositRequest;
import com.hub.brokeragefirm.dto.request.WithdrawRequest;
import com.hub.brokeragefirm.dto.response.AssetResponse;

import java.util.List;

public interface AssetService {
    List<AssetResponse> listAssets(Long customerId);
    void deposit(DepositRequest request);
    void withdraw(WithdrawRequest request);
}
