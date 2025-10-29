package com.payment.paymentsvc.mapper;

import com.payment.paymentsvc.dto.CreatePaymentTransactionRequest;
import com.payment.paymentsvc.dto.CreatePaymentTransactionResponse;
import com.payment.paymentsvc.enums.PaymentStatus;
import com.payment.paymentsvc.model.PaymentTransaction;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-29T23:48:03+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 21.0.8 (Ubuntu)"
)
@Component
public class PaymentTransactionMapperImpl implements PaymentTransactionMapper {

    @Override
    public PaymentTransaction toEntity(CreatePaymentTransactionRequest request) {
        if ( request == null ) {
            return null;
        }

        PaymentTransaction paymentTransaction = new PaymentTransaction();

        paymentTransaction.setAmount( request.amount() );
        paymentTransaction.setCurrency( request.currency() );

        return paymentTransaction;
    }

    @Override
    public CreatePaymentTransactionResponse toResponse(PaymentTransaction entity) {
        if ( entity == null ) {
            return null;
        }

        Long paymentTransactionId = null;
        String errorMessage = null;
        LocalDateTime executedAt = null;

        paymentTransactionId = entity.getId();
        errorMessage = entity.getErrorMessage();
        executedAt = entity.getExecutedAt();

        PaymentStatus transactionStatus = null;

        CreatePaymentTransactionResponse createPaymentTransactionResponse = new CreatePaymentTransactionResponse( paymentTransactionId, transactionStatus, errorMessage, executedAt );

        return createPaymentTransactionResponse;
    }
}
