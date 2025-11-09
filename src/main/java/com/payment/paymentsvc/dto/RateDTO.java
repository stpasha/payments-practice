package com.payment.paymentsvc.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public record RateDTO(LocalDateTime ld, String base, Map<String, BigDecimal> rates) {
}
