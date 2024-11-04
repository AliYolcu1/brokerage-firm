package com.hub.brokeragefirm.mapper;

import com.hub.brokeragefirm.dto.request.OrderRequest;
import com.hub.brokeragefirm.dto.response.OrderResponse;
import com.hub.brokeragefirm.entity.Order;
import com.hub.brokeragefirm.enums.OrderStatus;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = {OrderStatus.class, LocalDateTime.class})
public interface OrderMapper {

    default Order toEntity(OrderRequest request) {
        return Order.builder()
                .customerId(request.getCustomerId())
                .assetName(request.getAssetName())
                .orderSide(request.getOrderSide())
                .size(request.getSize())
                .price(request.getPrice())
                .status(OrderStatus.PENDING)
                .createDate(LocalDateTime.now())
                .build();
    }

    OrderResponse toDto(Order order);

    List<OrderResponse> toDtoList(List<Order> orders);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(OrderRequest dto, @MappingTarget Order entity);

    default Order createOrderFromRequest(OrderRequest request) {
        return Order.builder()
                .customerId(request.getCustomerId())
                .assetName(request.getAssetName())
                .orderSide(request.getOrderSide())
                .size(request.getSize())
                .price(request.getPrice())
                .status(OrderStatus.PENDING)
                .createDate(LocalDateTime.now())
                .build();
    }
}
