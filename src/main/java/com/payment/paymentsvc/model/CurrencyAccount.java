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
@Getter
@Setter
@NoArgsConstructor
@Table(name = "currency_account", schema = "payment_schema")
public class CurrencyAccount extends BaseEntity {

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Currency currency;

    @OneToMany(mappedBy = "sourceCurrencyAccount")
    private List<PaymentTransaction> sourceTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "destCurrencyAccount")
    private List<PaymentTransaction> destTransactions = new ArrayList<>();

}
