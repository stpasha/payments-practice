package com.payment.paymentsvc.service;

import com.payment.paymentsvc.dto.CreatePaymentTransactionRequest;
import com.payment.paymentsvc.dto.RefundPaymentTransactionRequest;
import com.payment.paymentsvc.model.BankAccount;
import com.payment.paymentsvc.model.PaymentTransaction;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentTransactionValidator {

    private final Validator validator;
    private final BankAccountService bankAccountService;
    private final PaymentTransactionService paymentTransactionService;


    public void validateCreatePaymentTransactionRequest(CreatePaymentTransactionRequest createPaymentTransactionRequest) {
        Set<ConstraintViolation<CreatePaymentTransactionRequest>> violations = validator.validate(createPaymentTransactionRequest);
        String[] messages = violations.stream().map(ConstraintViolation::getMessage).toArray(i -> new String[i]);
        if (messages.length > 0) {
            log.error("Error occurred during validation {}", Arrays.toString(messages));
            throw new ValidationException("Error occurred during validation " + Arrays.toString(messages));
        }
        BankAccount sourceAccount = bankAccountService.findById(createPaymentTransactionRequest.sourceBankAccountId())
                .orElseThrow(() -> new ValidationException("Source bank account not found " + createPaymentTransactionRequest.sourceBankAccountId()));

        if (sourceAccount.getBalance().compareTo(createPaymentTransactionRequest.amount()) < 0) {
            throw new ValidationException("Not sufficient funds on source account");
        }

        if (bankAccountService.findById(createPaymentTransactionRequest.destinationBankAccountId()).isEmpty()) {
            log.error("Destination bank account not found {}", createPaymentTransactionRequest.destinationBankAccountId());
            throw new ValidationException("Destination bank account not found " + createPaymentTransactionRequest.destinationBankAccountId());
        }
    }

    public void validateRefundPaymentTransactionRequest(RefundPaymentTransactionRequest refundPaymentTransactionRequest) {
        Set<ConstraintViolation<RefundPaymentTransactionRequest>> violations = validator.validate(refundPaymentTransactionRequest);
        String[] messages = violations.stream().map(ConstraintViolation::getMessage).toArray(i -> new String[i]);
        if (messages.length > 0) {
            log.error("Error occurred during validation {}", Arrays.toString(messages));
            throw new ValidationException("Error occurred during validation " + Arrays.toString(messages));
        }

        Optional<PaymentTransaction> paymentTransactionOptional = paymentTransactionService.findById(refundPaymentTransactionRequest.transactionId());
        PaymentTransaction paymentTransaction = paymentTransactionOptional.orElseThrow(() -> new ValidationException("Transaction not found " + refundPaymentTransactionRequest));
        BigDecimal totalAfterRefund = paymentTransaction.getRefunds().stream().map(refund -> refund.getRefundedAmount()).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);;
        if (totalAfterRefund.compareTo(paymentTransaction.getAmount()) >= 0) {
            throw new ValidationException("Refund amount exceeds original transaction amount");
        }
    }
}
