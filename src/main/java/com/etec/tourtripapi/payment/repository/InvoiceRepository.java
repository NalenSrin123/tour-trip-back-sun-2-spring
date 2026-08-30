package com.etec.tourtripapi.payment.repository;

import com.etec.tourtripapi.payment.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    boolean existsByInvoiceNo(String invoiceNo);
}
