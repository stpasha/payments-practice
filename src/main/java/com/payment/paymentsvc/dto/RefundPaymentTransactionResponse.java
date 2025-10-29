package com.payment.paymentsvc.dto;

import com.payment.paymentsvc.enums.PaymentStatus;

public record RefundPaymentTransactionResponse(Long refundId, PaymentStatus status, String message) {
}
