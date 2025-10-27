package com.payment.paymentsvc.model;

import com.payment.paymentsvc.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(schema = "payment_schema", name = "refund")
@Getter
@Setter
@NoArgsConstructor
public class Refund extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "payment_transaction_id", nullable = false)
    private PaymentTransaction paymentTransaction;

    @Column(nullable = false, name = "refunded_amount")
    private BigDecimal refundedAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column
    private String reason;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;


}
