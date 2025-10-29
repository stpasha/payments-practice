package com.payment.paymentsvc.controller;

import com.payment.paymentsvc.dto.CreatePaymentTransactionRequest;
import com.payment.paymentsvc.dto.RefundPaymentTransactionRequest;
import com.payment.paymentsvc.enums.CommandType;
import com.payment.paymentsvc.producer.PaymentTransactionProducer;
import com.payment.paymentsvc.util.JsonConverter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/v1/test")
@AllArgsConstructor
public class PaymentTestController {

    private final PaymentTransactionProducer paymentTransactionProducer;

    @PostMapping("/create")
    public ResponseEntity createTransfer(@RequestBody CreatePaymentTransactionRequest request) {
        paymentTransactionProducer.sendPaymentTransactionRequest(String.valueOf(ThreadLocalRandom.current().nextInt(1, 20000)), JsonConverter.toJson(request), CommandType.CREATE);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refund")
    public ResponseEntity createTransfer(@RequestBody RefundPaymentTransactionRequest request) {
        paymentTransactionProducer.sendPaymentTransactionRequest(String.valueOf(ThreadLocalRandom.current().nextInt(1, 20000)), JsonConverter.toJson(request), CommandType.REFUND);
        return ResponseEntity.ok().build();
    }
}
