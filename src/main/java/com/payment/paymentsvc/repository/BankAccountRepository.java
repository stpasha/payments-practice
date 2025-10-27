package com.payment.paymentsvc.repository;

import com.payment.paymentsvc.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.Long;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
