package com.payment.paymentsvc.repository;

import com.payment.paymentsvc.model.PaymentTransaction;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.Long;
import java.util.Optional;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    @EntityGraph(attributePaths = {"sourceBankAccount", "destBankAccount", "refunds"})
    @Query("SELECT pt FROM PaymentTransaction pt WHERE id = :id")
    Optional<PaymentTransaction> findByIdWithAccountsAndRefunds(@Param("id") Long id);
}
