package com.payment.paymentsvc.repository;

import com.payment.paymentsvc.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.Long;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
}
