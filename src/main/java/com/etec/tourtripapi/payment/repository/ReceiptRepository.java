package com.etec.tourtripapi.payment.repository;

import com.etec.tourtripapi.payment.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    boolean existsByReceiptNo(String receiptNo);
}
