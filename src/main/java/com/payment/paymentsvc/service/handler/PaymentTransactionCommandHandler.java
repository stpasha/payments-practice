package com.payment.paymentsvc.service.handler;

public interface PaymentTransactionCommandHandler {
    void process(String requestId, String message);
}
