package com.hub.brokeragefirm.repository;

import com.hub.brokeragefirm.entity.MoneyTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoneyTransferRepository extends JpaRepository<MoneyTransfer, Long> {
    List<MoneyTransfer> findByCustomerId(Long customerId);
}
