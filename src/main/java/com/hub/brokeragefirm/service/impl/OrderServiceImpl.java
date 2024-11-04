package com.hub.brokeragefirm.service.impl;

import com.hub.brokeragefirm.dto.request.OrderRequest;
import com.hub.brokeragefirm.dto.response.OrderResponse;
import com.hub.brokeragefirm.entity.Asset;
import com.hub.brokeragefirm.entity.Order;
import com.hub.brokeragefirm.enums.OrderSide;
import com.hub.brokeragefirm.enums.OrderStatus;
import com.hub.brokeragefirm.mapper.OrderMapper;
import com.hub.brokeragefirm.repository.AssetRepository;
import com.hub.brokeragefirm.repository.OrderRepository;
import com.hub.brokeragefirm.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AssetRepository assetRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        validateOrder(request);

        Asset asset;
        Asset tryAsset;

        if (request.getOrderSide() == OrderSide.SELL) {
            asset = lockAndValidateSellAsset(request);
            updateAssetForSell(asset, request.getSize());
        } else {
            tryAsset = lockAndValidateBuyAsset(request);
            updateAssetForBuy(tryAsset, request);
        }

        Order order = orderMapper.createOrderFromRequest(request);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public List<OrderResponse> listOrders(Long customerId, LocalDate startDate, LocalDate endDate) {
        List<Order> orders;

        if (startDate == null || endDate == null) {
            // Query only with customerId
            orders = orderRepository.findByCustomerId(customerId);
        } else {
            // Query with dates
            orders = orderRepository.findByCustomerIdAndCreateDateBetween(customerId, startDate.atStartOfDay(), endDate.atStartOfDay());
        }

        return orderMapper.toDtoList(orders);
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        validateCancellation(order);
        releaseLockAssets(order);

        order.setStatus(OrderStatus.CANCELED);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }


    private void validateOrder(OrderRequest request) {
        if (request.getSize().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Order size must be positive");
        }
        if (request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Order price must be positive");
        }
    }

    private Asset lockAndValidateSellAsset(OrderRequest request) {
        Asset asset = assetRepository.findByCustomerIdAndAssetName(request.getCustomerId(), request.getAssetName())
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        if (asset.getUsableSize().compareTo(request.getSize()) < 0) {
            throw new RuntimeException("Insufficient asset balance");
        }

        return asset;
    }

    private Asset lockAndValidateBuyAsset(OrderRequest request) {
        BigDecimal requiredAmount = request.getSize().multiply(request.getPrice());
        Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(request.getCustomerId(), "TRY")
                .orElseThrow(() -> new RuntimeException("TRY asset not found"));

        if (tryAsset.getUsableSize().compareTo(requiredAmount) < 0) {
            throw new RuntimeException("Insufficient TRY balance");
        }

        return tryAsset;
    }

    private void updateAssetForSell(Asset asset, BigDecimal size) {
        asset.setUsableSize(asset.getUsableSize().subtract(size));
        assetRepository.save(asset);
    }

    private void updateAssetForBuy(Asset tryAsset, OrderRequest request) {
        BigDecimal requiredAmount = request.getSize().multiply(request.getPrice());
        tryAsset.setUsableSize(tryAsset.getUsableSize().subtract(requiredAmount));
        assetRepository.save(tryAsset);
    }

    private void validateCancellation(Order order) {
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders can be canceled");
        }
    }

    private void releaseLockAssets(Order order) {
        if (order.getOrderSide() == OrderSide.SELL) {
            releaseLockedSellAsset(order);
        } else {
            releaseLockedBuyAsset(order);
        }
    }

    private void releaseLockedSellAsset(Order order) {
        Asset asset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName())
                .orElseThrow(() -> new RuntimeException("Asset not found"));
        asset.setUsableSize(asset.getUsableSize().add(order.getSize()));
        assetRepository.save(asset);
    }

    private void releaseLockedBuyAsset(Order order) {
        BigDecimal lockedAmount = order.getSize().multiply(order.getPrice());
        Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), "TRY")
                .orElseThrow(() -> new RuntimeException("TRY asset not found"));
        tryAsset.setUsableSize(tryAsset.getUsableSize().add(lockedAmount));
        assetRepository.save(tryAsset);
    }
}
