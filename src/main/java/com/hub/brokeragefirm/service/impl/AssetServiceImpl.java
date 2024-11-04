package com.hub.brokeragefirm.service.impl;

import com.hub.brokeragefirm.dto.request.MoneyTransferRequest;
import com.hub.brokeragefirm.dto.response.AssetResponse;
import com.hub.brokeragefirm.dto.response.MoneyTransferResponse;
import com.hub.brokeragefirm.entity.Asset;
import com.hub.brokeragefirm.entity.MoneyTransfer;
import com.hub.brokeragefirm.enums.TransferType;
import com.hub.brokeragefirm.mapper.AssetMapper;
import com.hub.brokeragefirm.mapper.MoneyTransferMapper;
import com.hub.brokeragefirm.repository.AssetRepository;
import com.hub.brokeragefirm.repository.MoneyTransferRepository;
import com.hub.brokeragefirm.service.AssetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final MoneyTransferRepository moneyTransferRepository;
    private final AssetMapper assetMapper;
    private final MoneyTransferMapper moneyTransferMapper;

    @Override
    public List<AssetResponse> listAssets(Long customerId) {
        List<Asset> assets = assetRepository.findByCustomerId(customerId);
        return assetMapper.toDtoList(assets);
    }

    @Override
    @Transactional
    public MoneyTransferResponse deposit(MoneyTransferRequest request) {
        Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(request.getCustomerId(), "TRY")
                .orElseGet(() -> createNewTryAsset(request.getCustomerId()));

        tryAsset.setSize(tryAsset.getSize().add(request.getAmount()));
        tryAsset.setUsableSize(tryAsset.getUsableSize().add(request.getAmount()));

        assetRepository.save(tryAsset);

        // Create money transfer record for deposit
         return moneyTransferMapper.toDto(createMoneyTransferRecord(request, tryAsset.getSize(),TransferType.DEPOSIT));
    }

    @Override
    @Transactional
    public MoneyTransferResponse withdraw(MoneyTransferRequest request) {
        Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(request.getCustomerId(), "TRY")
                .orElseThrow(() -> new RuntimeException("TRY asset not found"));

        if (tryAsset.getUsableSize().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient TRY balance");
        }

        tryAsset.setSize(tryAsset.getSize().subtract(request.getAmount()));
        tryAsset.setUsableSize(tryAsset.getUsableSize().subtract(request.getAmount()));
        assetRepository.save(tryAsset);

        // Create money transfer record for withdraw
        return moneyTransferMapper.toDto(createMoneyTransferRecord(request, tryAsset.getSize(),TransferType.WITHDRAW));

    }

    private Asset createNewTryAsset(Long customerId) {
        return Asset.builder()
                .customerId(customerId)
                .assetName("TRY")
                .size(BigDecimal.ZERO)
                .usableSize(BigDecimal.ZERO)
                .build();
    }

    private MoneyTransfer createMoneyTransferRecord(MoneyTransferRequest request, BigDecimal balanceAfterTransaction, TransferType type){

        MoneyTransfer moneyTransfer = MoneyTransfer.builder()
                .customerId(request.getCustomerId())
                .type(type)
                .amount(request.getAmount())
                .balanceAfterTransaction(balanceAfterTransaction)
                .iban(request.getIban())
                .transactionDate(LocalDateTime.now())
                .build();

        return moneyTransferRepository.save(moneyTransfer);

    }
}
