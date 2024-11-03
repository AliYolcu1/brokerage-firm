package com.hub.brokeragefirm.repository;

import com.hub.brokeragefirm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerIdAndCreateDateBetween(Long customerId, LocalDate startDate, LocalDate endDate);

    List<Order> findByCustomerId(Long customerId);
}
