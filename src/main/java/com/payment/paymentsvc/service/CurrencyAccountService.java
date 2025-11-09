package com.payment.paymentsvc.service;

import com.payment.paymentsvc.model.CurrencyAccount;
import com.payment.paymentsvc.repository.CurrencyAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyAccountService {

    private final CurrencyAccountRepository currencyAccountRepository;

    Optional<CurrencyAccount> findById(Long id) {
        return currencyAccountRepository.findById(id);
    }
}
