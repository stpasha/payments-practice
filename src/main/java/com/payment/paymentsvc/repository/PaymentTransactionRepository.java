package com.payment.paymentsvc.repository;

import com.payment.paymentsvc.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.Long;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
}
