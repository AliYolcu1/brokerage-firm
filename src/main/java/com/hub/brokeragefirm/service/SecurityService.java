package com.hub.brokeragefirm.service;

public interface SecurityService {
    boolean hasCustomerAccess(Long customerId);
    boolean hasOrderAccess(Long orderId);
}
