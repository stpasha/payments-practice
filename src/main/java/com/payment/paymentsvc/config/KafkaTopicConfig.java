package com.payment.paymentsvc.config;

import com.payment.paymentsvc.util.TopicConfig;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@ConfigurationProperties("spring.kafka")
public class KafkaTopicConfig {
    public static final String SPRING_KAFKA_BOOTSTRAP_SERVERS = "spring.kafka.bootstrap-servers";
    private List<TopicConfig> topics;

    public List<TopicConfig> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicConfig> topics) {
        this.topics = topics;
    }

    @Bean
    public KafkaAdmin kafkaAdmin(Environment env) {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty(SPRING_KAFKA_BOOTSTRAP_SERVERS));
        configs.put("offset.topic.replication.factor", "1");
        configs.put("transaction.state.log.replication.factor", "1");
        configs.put("transaction.state.log.min.isr", "1");
        return new KafkaAdmin(configs);
    }

    @Bean
    public List<NewTopic> createTopics() {
        return topics.stream().map(topicConfig -> new NewTopic(topicConfig.name(), topicConfig.partitions(), topicConfig.replicationFactor())).collect(Collectors.toList());
    }
}
