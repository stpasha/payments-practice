package com.payment.paymentsvc.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RefundPaymentTransactionRequest(@NotNull Long transactionId, @NotNull @Min(value = 0, message = "Refunded amount must be greater then 0") BigDecimal refundedAmount, String reason) {
}
