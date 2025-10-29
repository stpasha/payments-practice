package com.payment.paymentsvc.model;

import com.payment.paymentsvc.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(schema = "payment_schema", name = "refund")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Refund extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "payment_transaction_id", nullable = false)
    private PaymentTransaction paymentTransaction;

    @Column(nullable = false, name = "refunded_amount")
    private BigDecimal refundedAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column
    private String reason;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;


}
