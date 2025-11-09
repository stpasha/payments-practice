package com.payment.paymentsvc.mapper;

import com.payment.paymentsvc.dto.CreatePaymentTransactionRequest;
import com.payment.paymentsvc.dto.CreatePaymentTransactionResponse;
import com.payment.paymentsvc.model.PaymentTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentTransactionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "errorMessage", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "executedAt", ignore = true)
    @Mapping(target = "refunds", ignore = true)
    @Mapping(target = "sourceCurrencyAccount", ignore = true)
    @Mapping(target = "destCurrencyAccount", ignore = true)
    PaymentTransaction toEntity(CreatePaymentTransactionRequest request);

    @Mapping(target = "paymentTransactionId", source = "id")
    @Mapping(target = "transactionStatus", ignore = true)
    CreatePaymentTransactionResponse toResponse(PaymentTransaction entity);
}
