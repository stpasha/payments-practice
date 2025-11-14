package com.payment.paymentsvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class WebConfig {
    @Bean
    RestClient ratesRestClient() {
        return RestClient.builder().baseUrl("https://api.currencyfreaks.com/").build();
    }
}
