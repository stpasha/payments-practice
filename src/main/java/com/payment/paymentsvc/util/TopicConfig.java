package com.payment.paymentsvc.util;

public record TopicConfig(String name, int partitions, short replicationFactor) {
}
