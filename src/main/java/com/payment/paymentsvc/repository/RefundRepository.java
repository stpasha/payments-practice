package com.payment.paymentsvc.repository;

import com.payment.paymentsvc.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.Long;

public interface RefundRepository extends JpaRepository<Refund, Long> {
}
