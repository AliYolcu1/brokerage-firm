package com.hub.brokeragefirm.service;

import com.hub.brokeragefirm.dto.request.OrderRequest;
import com.hub.brokeragefirm.dto.response.OrderResponse;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    List<OrderResponse> listOrders(Long customerId, LocalDate startDate, LocalDate endDate);

    OrderResponse cancelOrder(Long orderId);

}
