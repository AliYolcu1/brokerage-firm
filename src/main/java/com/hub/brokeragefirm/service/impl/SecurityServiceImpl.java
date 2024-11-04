package com.hub.brokeragefirm.service.impl;

import com.hub.brokeragefirm.entity.Customer;
import com.hub.brokeragefirm.entity.Order;
import com.hub.brokeragefirm.repository.CustomerRepository;
import com.hub.brokeragefirm.repository.EmployeeRepository;
import com.hub.brokeragefirm.repository.OrderRepository;
import com.hub.brokeragefirm.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @Override
    public boolean hasCustomerAccess(Long customerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return false;
        }

        // check if the user is employee(admin)
        if (isEmployee(auth.getName())) {
            return true;  // Employees can access everything
        }

        // Customer check
        return isCustomerAuthorized(auth.getName(), customerId);
    }

    @Override
    public boolean hasOrderAccess(Long orderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return false;
        }

        // Employee check
        if (isEmployee(auth.getName())) {
            return true;
        }

        // Check if it's customer's own order
        return isOrderBelongsToCustomer(auth.getName(), orderId);
    }

    private boolean isEmployee(String username) {
        return employeeRepository.findByUsername(username).isPresent();
    }

    private boolean isCustomerAuthorized(String username, Long customerId) {
        return customerRepository.findByUsername(username)
                .map(customer -> customer.getId().equals(customerId))
                .orElse(false);
    }

    private boolean isOrderBelongsToCustomer(String username, Long orderId) {
        Customer customer = customerRepository.findByUsername(username).orElse(null);
        if (customer == null) {
            return false;
        }

        Order order = orderRepository.findById(orderId).orElse(null);
        return order != null && order.getCustomerId().equals(customer.getId());
    }
}
