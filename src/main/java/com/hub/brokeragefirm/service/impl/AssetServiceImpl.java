package com.hub.brokeragefirm.service.impl;

import com.hub.brokeragefirm.dto.request.DepositRequest;
import com.hub.brokeragefirm.dto.request.WithdrawRequest;
import com.hub.brokeragefirm.dto.response.AssetResponse;
import com.hub.brokeragefirm.entity.Asset;
import com.hub.brokeragefirm.mapper.AssetMapper;
import com.hub.brokeragefirm.repository.AssetRepository;
import com.hub.brokeragefirm.service.AssetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;

    @Override
    public List<AssetResponse> listAssets(Long customerId) {
        List<Asset> assets = assetRepository.findByCustomerId(customerId);
        return assetMapper.toDtoList(assets);
    }

    @Override
    @Transactional
    public void deposit(DepositRequest request) {
        Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(request.getCustomerId(), "TRY")
                .orElseGet(() -> createNewTryAsset(request.getCustomerId()));

        tryAsset.setSize(tryAsset.getSize().add(request.getAmount()));
        tryAsset.setUsableSize(tryAsset.getUsableSize().add(request.getAmount()));
        assetRepository.save(tryAsset);
    }

    @Override
    @Transactional
    public void withdraw(WithdrawRequest request) {
        Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(request.getCustomerId(), "TRY")
                .orElseThrow(() -> new RuntimeException("TRY asset not found"));

        if (tryAsset.getUsableSize().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient TRY balance");
        }

        tryAsset.setSize(tryAsset.getSize().subtract(request.getAmount()));
        tryAsset.setUsableSize(tryAsset.getUsableSize().subtract(request.getAmount()));
        assetRepository.save(tryAsset);
    }

    private Asset createNewTryAsset(Long customerId) {
        return Asset.builder()
                .customerId(customerId)
                .assetName("TRY")
                .size(BigDecimal.ZERO)
                .usableSize(BigDecimal.ZERO)
                .build();
    }
}
