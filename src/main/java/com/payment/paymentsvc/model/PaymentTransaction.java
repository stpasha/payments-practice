package com.payment.paymentsvc.model;

import com.payment.paymentsvc.enums.Currency;
import com.payment.paymentsvc.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Table(schema = "payment_schema", name = "payment_transaction")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class PaymentTransaction extends BaseEntity {

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(name = "error_message")
    private String errorMessage;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "executed_at")
    private LocalDateTime executedAt;

    @ManyToOne
    @JoinColumn(name = "src_currency_account_id")
    private CurrencyAccount sourceCurrencyAccount;

    @ManyToOne
    @JoinColumn(name = "dst_currency_account_id")
    private CurrencyAccount destCurrencyAccount;

    @OneToMany(mappedBy = "paymentTransaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Refund> refunds;

}
