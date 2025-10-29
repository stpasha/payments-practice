package com.payment.paymentsvc.dto;

import com.payment.paymentsvc.enums.TransactionStatus;

import java.time.LocalDateTime;

public record CreatePaymentTransactionResponse(Long paymentTransactionId, TransactionStatus transactionStatus, String errorMessage, LocalDateTime executedAt) {
}
