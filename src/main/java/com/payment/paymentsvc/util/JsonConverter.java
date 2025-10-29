package com.payment.paymentsvc.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.paymentsvc.dto.CreatePaymentTransactionRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class JsonConverter {

    private ObjectMapper objectMapper = new ObjectMapper();

    public<T> T toRequestObject(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            log.error("Unable to convert {} to CreatePaymentTransactionRequest", json);
            throw new IllegalArgumentException("Unnable convert to CreatePaymentTransactionRequest");
        }
    }

    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Unnable convert to Json");
        }
    }
}
