package com.payment.paymentsvc.producer;

import com.payment.paymentsvc.enums.CommandType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentTransactionProducer {
    public static final String HEADER_NAME = "command";
    @Value("${spring.kafka.topic.result}")
    private String resultTopic;
    @Value("${spring.kafka.topic.source}")
    private String sourceTopic;
    private final KafkaTemplate<String,String> kafkaTemplate;

    public void sendPaymentTransactionResponse(String requestId, String message, CommandType commandType) {
        kafkaTemplate.send(buildMessage(requestId, message, commandType, resultTopic));
    }

    public void sendPaymentTransactionRequest(String requestId, String message, CommandType commandType) {
        kafkaTemplate.send(buildMessage(requestId, message, commandType, sourceTopic));
    }

    private Message<String> buildMessage(String requestId, String message, CommandType commandType, String topic) {
        return MessageBuilder.withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.KEY, requestId)
                .setHeader(HEADER_NAME, commandType.name())
                .build();
    }
}
