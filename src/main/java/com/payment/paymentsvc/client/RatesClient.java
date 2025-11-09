package com.payment.paymentsvc.client;

import com.payment.paymentsvc.dto.RateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RatesClient {
    public static final String APIKEY = "apikey";
    private final RestClient ratesClient;
    @Value("${spring.client.rates.token}")
    private final String token;


    public RateDTO getRatesData(String symbols) {
        return ratesClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("v2.0/rates/latest")
                        .queryParam(APIKEY, token)
                        .queryParam("symbols", symbols).build())
                .retrieve().body(RateDTO.class);
    }

}
