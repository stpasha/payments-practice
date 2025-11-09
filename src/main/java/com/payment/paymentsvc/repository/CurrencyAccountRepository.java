package com.payment.paymentsvc.repository;

import com.payment.paymentsvc.model.CurrencyAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyAccountRepository extends JpaRepository<CurrencyAccount, Long> {
}
