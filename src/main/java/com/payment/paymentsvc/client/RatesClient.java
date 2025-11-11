package com.payment.paymentsvc.client;

import com.payment.paymentsvc.dto.RateDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;



@Component
public class RatesClient {
    public static final String APIKEY = "apikey";
    private final RestClient ratesClient;
    private final String token;

    public RatesClient(RestClient ratesClient,@Value("${spring.client.rates.token}") String token) {
        this.ratesClient = ratesClient;
        this.token = token;
    }

    public RateDTO getRatesData(String symbols) {
        return ratesClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("v2.0/rates/latest")
                        .queryParam(APIKEY, token)
                        .queryParam("symbols", symbols).build())
                .retrieve().body(RateDTO.class);
    }

}
