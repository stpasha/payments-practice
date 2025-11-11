package com.payment.paymentsvc.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.kafka.topic")
public record KafkaProperties(String result, String source) {
}
