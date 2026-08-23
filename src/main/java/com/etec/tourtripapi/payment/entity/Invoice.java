package com.etec.tourtripapi.payment.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "invoices")
@SQLDelete(sql = "UPDATE invoices SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(name = "invoice_no",unique = true)
    private String invoiceNo;

    @Column(name = "sub_total")
    private BigDecimal subTotal;

    @Column(name = "tax_amount")
    private BigDecimal taxAmount;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "issued_at")
    @CreationTimestamp()
    private LocalDateTime  issuedAt;

    @Column(name = "is_deleted",nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "invoice")
    private List<Payment> payments;


}
