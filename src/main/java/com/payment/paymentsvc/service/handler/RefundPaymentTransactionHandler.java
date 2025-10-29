package com.payment.paymentsvc.service.handler;

import com.payment.paymentsvc.dto.RefundPaymentTransactionRequest;
import com.payment.paymentsvc.dto.RefundPaymentTransactionResponse;
import com.payment.paymentsvc.enums.CommandType;
import com.payment.paymentsvc.producer.PaymentTransactionProducer;
import com.payment.paymentsvc.service.PaymentTransactionService;
import com.payment.paymentsvc.service.PaymentTransactionValidator;
import com.payment.paymentsvc.util.JsonConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefundPaymentTransactionHandler implements PaymentTransactionCommandHandler {

    private final PaymentTransactionValidator paymentTransactionValidator;
    private final PaymentTransactionService paymentTransactionService;
    private final PaymentTransactionProducer paymentTransactionProducer;

    @Override
    public void process(String requestId, String message) {
        RefundPaymentTransactionRequest requestObject = JsonConverter.toRequestObject(message, RefundPaymentTransactionRequest.class);
        paymentTransactionValidator.validateRefundPaymentTransactionRequest(requestObject);
        RefundPaymentTransactionResponse paymentTransactionResponse = paymentTransactionService.refundPayment(requestObject);
        paymentTransactionProducer.sendPaymentTransactionResponse(requestId, JsonConverter.toJson(paymentTransactionResponse), CommandType.REFUND);
    }
}
