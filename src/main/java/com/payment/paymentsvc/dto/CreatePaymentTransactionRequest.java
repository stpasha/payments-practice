package com.payment.paymentsvc.dto;

import com.payment.paymentsvc.enums.Currency;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreatePaymentTransactionRequest(@NotNull Long sourceAccountId, Long destinationAccountId,
                                              @NotNull BigDecimal amount,
                                              @NotNull Currency currency, String description) {
}
