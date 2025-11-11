package com.payment.paymentsvc.producer;

import com.payment.paymentsvc.enums.CommandType;
import com.payment.paymentsvc.util.KafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class PaymentTransactionProducer {
    public static final String HEADER_NAME = "command";
    private final KafkaProperties kafkaProperties;
    private final KafkaTemplate<String,String> kafkaTemplate;

    public PaymentTransactionProducer(KafkaProperties kafkaProperties, KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaProperties = kafkaProperties;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentTransactionResponse(String requestId, String message, CommandType commandType) {
        kafkaTemplate.send(buildMessage(requestId, message, commandType, kafkaProperties.result()));
    }

    public void sendPaymentTransactionRequest(String requestId, String message, CommandType commandType) {
        kafkaTemplate.send(buildMessage(requestId, message, commandType, kafkaProperties.source()));
    }

    private Message<String> buildMessage(String requestId, String message, CommandType commandType, String topic) {
        return MessageBuilder.withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.KEY, requestId)
                .setHeader(HEADER_NAME, commandType.name())
                .build();
    }
}
