package com.payment.paymentsvc.consumer;

import com.payment.paymentsvc.enums.CommandType;
import com.payment.paymentsvc.service.handler.PaymentTransactionCommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentTransactionCommandListener {
    private final Map<CommandType, PaymentTransactionCommandHandler> commandHandlers;

    @KafkaListener(topics = "payment-command", containerFactory = "kafkaListenerContainerFactory")
    public void consumePaymentTransactionCommand(ConsumerRecord<String,String> record) {
        CommandType commandType = getCommandType(record);
        if (CommandType.UNKNOWN.equals(commandType)) {
            log.error("Unsupported command {}", record.headers().lastHeader("command"));
            throw new IllegalArgumentException("Unsupported command");
        }
        commandHandlers.get(commandType).process(record.key(), record.value());
    }

    private CommandType getCommandType(ConsumerRecord<String,String> record) {
        Header command = record.headers().lastHeader("command");
        if (command != null) {
            return CommandType.fromString(new String(command.value()));
        }
        return CommandType.UNKNOWN;
    }
}
