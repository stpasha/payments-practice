package com.payment.paymentsvc.repository;

import com.payment.paymentsvc.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.Long;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
}
