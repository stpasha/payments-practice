package com.payment.paymentsvc.dto;

import com.payment.paymentsvc.enums.PaymentStatus;

import java.time.LocalDateTime;

public record CreatePaymentTransactionResponse(Long paymentTransactionId, PaymentStatus transactionStatus, String errorMessage, LocalDateTime executedAt) {
}
