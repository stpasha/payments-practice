package com.payment.paymentsvc.service;

import com.payment.paymentsvc.client.RatesClient;
import com.payment.paymentsvc.dto.*;
import com.payment.paymentsvc.enums.PaymentStatus;
import com.payment.paymentsvc.mapper.PaymentTransactionMapper;
import com.payment.paymentsvc.model.CurrencyAccount;
import com.payment.paymentsvc.model.PaymentTransaction;
import com.payment.paymentsvc.model.Refund;
import com.payment.paymentsvc.repository.CurrencyAccountRepository;
import com.payment.paymentsvc.repository.PaymentTransactionRepository;
import com.payment.paymentsvc.repository.RefundRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.validation.ValidationException;

@Service
@RequiredArgsConstructor
public class PaymentTransactionService {

    private final RatesClient ratesClient;
    private final CurrencyAccountRepository currBankAccountRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final RefundRepository refundRepository;
    private final PaymentTransactionMapper paymentTransactionMapper;

    @Transactional
    public CreatePaymentTransactionResponse createPayment(CreatePaymentTransactionRequest request) {
        CurrencyAccount sourceAccount = currBankAccountRepository.findById(request.sourceAccountId())
                .orElseThrow(() -> new ValidationException(
                        "Source bank account not found: " + request.sourceAccountId()));

        CurrencyAccount destinationAccount = currBankAccountRepository.findById(request.destinationAccountId())
                .orElse(null);
        String from = request.currency().name();
        String sourceCur = sourceAccount.getCurrency().name();
        String destCur = destinationAccount != null ? destinationAccount.getCurrency().name() : null;

        String symbols = destCur != null
                ? String.join(",", from, sourceCur, destCur)
                : String.join(",", from, sourceCur);

        RateDTO rates = ratesClient.getRatesData(symbols);

        if (sourceAccount.getBalance().compareTo(request.amount()) < 0) {
            throw new ValidationException("Not enough funds on source account");
        }

        BigDecimal sourceAmount = convert(request.amount(), from, sourceCur, rates);
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(sourceAmount));
        if (destinationAccount != null) {
            BigDecimal destAmount = convert(request.amount(), from, destCur, rates);
            destinationAccount.setBalance(destinationAccount.getBalance().add(destAmount));
        }
        PaymentTransaction transaction = paymentTransactionMapper.toEntity(request);
        transaction.setSourceCurrencyAccount(sourceAccount);
        transaction.setDestCurrencyAccount(destinationAccount);
        transaction.setStatus(PaymentStatus.CREATED);
        transaction.setExecutedAt(LocalDateTime.now());
        paymentTransactionRepository.save(transaction);

        return paymentTransactionMapper.toResponse(transaction);
    }

    @Transactional
    public RefundPaymentTransactionResponse refundPayment(RefundPaymentTransactionRequest request) {
        PaymentTransaction paymentTransaction = paymentTransactionRepository.findById(request.transactionId()).orElseThrow(() -> new ValidationException("Payment Transaction notFound"));
        BigDecimal totalAfterRefund = paymentTransaction.getRefunds().stream().map(refund -> refund.getRefundedAmount()).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        if (totalAfterRefund.compareTo(paymentTransaction.getAmount()) >= 0) {
            throw new ValidationException("Refund amount exceeds original transaction amount");
        }
        CurrencyAccount sourceBankAccount = paymentTransaction.getSourceCurrencyAccount();
        CurrencyAccount destBankAccount = paymentTransaction.getDestCurrencyAccount();
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


    private static BigDecimal convert(BigDecimal amount, String from, String to, RateDTO rates) {
        BigDecimal rateFrom = rates.rates().get(from);
        BigDecimal rateTo = rates.rates().get(to);

        if (rateFrom == null || rateTo == null) {
            throw new ValidationException("Missing currency rate for: " + from + " or " + to);
        }

        return amount.multiply(rateTo).divide(rateFrom, 2, RoundingMode.HALF_UP);
    }

}
