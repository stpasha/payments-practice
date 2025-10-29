package com.payment.paymentsvc.dto;

import com.payment.paymentsvc.enums.TransactionStatus;

public record RefundPaymentTransactionResponse(Long refundId, TransactionStatus status, String message) {
}
