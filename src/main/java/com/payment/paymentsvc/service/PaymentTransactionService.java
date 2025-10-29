package com.payment.paymentsvc.service;

import com.payment.paymentsvc.dto.CreatePaymentTransactionRequest;
import com.payment.paymentsvc.dto.CreatePaymentTransactionResponse;
import com.payment.paymentsvc.dto.RefundPaymentTransactionRequest;
import com.payment.paymentsvc.dto.RefundPaymentTransactionResponse;
import com.payment.paymentsvc.enums.PaymentStatus;
import com.payment.paymentsvc.enums.PaymentStatus;
import com.payment.paymentsvc.mapper.PaymentTransactionMapper;
import com.payment.paymentsvc.model.BankAccount;
import com.payment.paymentsvc.model.PaymentTransaction;
import com.payment.paymentsvc.model.Refund;
import com.payment.paymentsvc.repository.BankAccountRepository;
import com.payment.paymentsvc.repository.PaymentTransactionRepository;
import com.payment.paymentsvc.repository.RefundRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.validation.ValidationException;

@Service
@RequiredArgsConstructor
public class PaymentTransactionService {

    private final BankAccountRepository bankAccountRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final RefundRepository refundRepository;
    private final PaymentTransactionMapper paymentTransactionMapper;

    @Transactional
    public CreatePaymentTransactionResponse createPayment(CreatePaymentTransactionRequest request) {
        BankAccount sourceAccount = bankAccountRepository.findById(request.sourceBankAccountId())
                .orElseThrow(() -> new ValidationException(
                        "Source bank account not found: " + request.sourceBankAccountId()));

        BankAccount destinationAccount = bankAccountRepository.findById(request.destinationBankAccountId())
                .orElseThrow(() -> new ValidationException(
                        "Destination bank account not found: " + request.destinationBankAccountId()));

        if (sourceAccount.getBalance().compareTo(request.amount()) < 0) {
            throw new ValidationException("Not enough funds on source account");
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(request.amount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(request.amount()));

        bankAccountRepository.save(sourceAccount);
        bankAccountRepository.save(destinationAccount);

        PaymentTransaction transaction = paymentTransactionMapper.toEntity(request);
        transaction.setSourceBankAccount(sourceAccount);
        transaction.setDestBankAccount(destinationAccount);
        transaction.setStatus(PaymentStatus.CREATED);
        transaction.setExecutedAt(LocalDateTime.now());
        paymentTransactionRepository.save(transaction);

        return paymentTransactionMapper.toResponse(transaction);
    }

    @Transactional
    public RefundPaymentTransactionResponse refundPayment(RefundPaymentTransactionRequest request) {
        PaymentTransaction paymentTransaction = paymentTransactionRepository.findById(request.transactionId()).orElseThrow(() -> new ValidationException("Payment Transaction notFound"));
        BankAccount sourceBankAccount = paymentTransaction.getSourceBankAccount();
        BankAccount destBankAccount = paymentTransaction.getDestBankAccount();
        sourceBankAccount.setBalance(sourceBankAccount.getBalance().add(request.refundedAmount()));
        destBankAccount.setBalance(destBankAccount.getBalance().subtract(request.refundedAmount()));
        Refund refund = Refund.builder()
                .refundedAmount(request.refundedAmount())
                .paymentTransaction(paymentTransaction)
                .reason(request.reason())
                .status(PaymentStatus.REFUNDED).build();
        Refund saved = refundRepository.save(refund);
        return new RefundPaymentTransactionResponse(saved.getId(), PaymentStatus.PROCESSED, "");

    }

    public Optional<PaymentTransaction> findById(Long id) {
        return paymentTransactionRepository.findByIdWithAccountsAndRefunds(id);
    }
}
