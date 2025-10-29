package com.payment.paymentsvc.config;

import com.payment.paymentsvc.enums.CommandType;
import com.payment.paymentsvc.service.handler.CreatePaymentTransactionHandler;
import com.payment.paymentsvc.service.handler.PaymentTransactionCommandHandler;
import com.payment.paymentsvc.service.handler.RefundPaymentTransactionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class PaymentCommandConfig {
    @Bean
    public Map<CommandType, PaymentTransactionCommandHandler> commandHandlers(
            CreatePaymentTransactionHandler createPaymentTransactionHandler,
            RefundPaymentTransactionHandler refundPaymentTransactionHandler) {
        return Map.of(CommandType.CREATE, createPaymentTransactionHandler, CommandType.REFUND, refundPaymentTransactionHandler);
    }
}
