package com.payment.paymentsvc.service.handler;

import com.payment.paymentsvc.dto.CreatePaymentTransactionRequest;
import com.payment.paymentsvc.dto.CreatePaymentTransactionResponse;
import com.payment.paymentsvc.enums.CommandType;
import com.payment.paymentsvc.producer.PaymentTransactionProducer;
import com.payment.paymentsvc.service.PaymentTransactionService;
import com.payment.paymentsvc.service.PaymentTransactionValidator;
import com.payment.paymentsvc.util.JsonConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CreatePaymentTransactionHandler implements PaymentTransactionCommandHandler {

    private final PaymentTransactionValidator paymentTransactionValidator;
    private final PaymentTransactionService paymentTransactionService;
    private final PaymentTransactionProducer paymentTransactionProducer;

    @Override
    public void process(String requestId, String message) {
        CreatePaymentTransactionRequest requestObject = JsonConverter.toRequestObject(message, CreatePaymentTransactionRequest.class);
        paymentTransactionValidator.validateCreatePaymentTransactionRequest(requestObject);
        CreatePaymentTransactionResponse payment = paymentTransactionService.createPayment(requestObject);
        paymentTransactionProducer.sendPaymentTransactionResponse(requestId, JsonConverter.toJson(payment), CommandType.CREATE);
    }
}
