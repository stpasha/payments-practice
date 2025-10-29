package com.payment.paymentsvc.repository;

import com.payment.paymentsvc.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.Long;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
