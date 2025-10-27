package com.payment.paymentsvc.model;

import com.payment.paymentsvc.enums.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "payment_schema", name = "bank_account")
@Getter
@Setter
@NoArgsConstructor
public class BankAccount extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String number;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Currency currency;

    @OneToMany(mappedBy = "sourceBankAccount")
    private List<PaymentTransaction> sourceTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "destBankAccount")
    private List<PaymentTransaction> destTransactions = new ArrayList<>();
}
