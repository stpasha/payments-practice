package com.payment.paymentsvc.service;

import com.payment.paymentsvc.dto.CreatePaymentTransactionRequest;
import com.payment.paymentsvc.dto.RefundPaymentTransactionRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentTransactionValidator {

    private final Validator validator;


    public void validateCreatePaymentTransactionRequest(CreatePaymentTransactionRequest createPaymentTransactionRequest) {
        Set<ConstraintViolation<CreatePaymentTransactionRequest>> violations = validator.validate(createPaymentTransactionRequest);
        String[] messages = violations.stream().map(ConstraintViolation::getMessage).toArray(i -> new String[i]);
        if (messages.length > 0) {
            log.error("Error occurred during validation {}", Arrays.toString(messages));
            throw new ValidationException("Error occurred during validation " + Arrays.toString(messages));
        }
    }

    public void validateRefundPaymentTransactionRequest(RefundPaymentTransactionRequest refundPaymentTransactionRequest) {
        Set<ConstraintViolation<RefundPaymentTransactionRequest>> violations = validator.validate(refundPaymentTransactionRequest);
        String[] messages = violations.stream().map(ConstraintViolation::getMessage).toArray(i -> new String[i]);
        if (messages.length > 0) {
            log.error("Error occurred during validation {}", Arrays.toString(messages));
            throw new ValidationException("Error occurred during validation " + Arrays.toString(messages));
        }
    }
}
