package com.hub.brokeragefirm.controller;

import com.hub.brokeragefirm.dto.request.OrderRequest;
import com.hub.brokeragefirm.dto.response.OrderResponse;
import com.hub.brokeragefirm.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') or @securityServiceImpl.hasCustomerAccess(#request.customerId)")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN') or @securityServiceImpl.hasCustomerAccess(#customerId)")
    public ResponseEntity<List<OrderResponse>> listOrders(
            @RequestParam Long customerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(orderService.listOrders(customerId, startDate, endDate));
    }

    @PostMapping("/cancel/{orderId}")
    @PreAuthorize("hasRole('ADMIN') or @securityServiceImpl.hasOrderAccess(#orderId)")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId) {
        OrderResponse response = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(response);
    }

}
