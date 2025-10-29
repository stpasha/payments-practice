package com.payment.paymentsvc.service;

import com.payment.paymentsvc.model.BankAccount;
import com.payment.paymentsvc.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    Optional<BankAccount> findById(Long id) {
        return bankAccountRepository.findById(id);
    }
}
