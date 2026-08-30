package com.etec.tourtripapi.payment.entity;

import com.etec.tourtripapi.common.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "receipts")
@SQLDelete(sql = "UPDATE receipt SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Receipt {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "receipt")
    private Payment payment;

    @Column(name = "receipt_no",unique = true)
    private String receiptNo;

    @Column(name = "issued_at",insertable = false, updatable = false)
    private LocalDateTime issuedAt;

    @Column(name = "tour_tittle")
    private String tourTitle;

    @Column(name = "tour_date")
    private LocalDateTime tourDate;

    @Column(name = "num_travelers")
    private Integer numTravelers;

    @Column(name = "sub_total")
    private BigDecimal subTotal;

    @Column(name = "tax_amount")
    private BigDecimal taxAmount;

    @Column(name = "total_paid")
    private BigDecimal totalPaid;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "pdf_url")
    private String pdfUrl;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    
}
