package com.usfq.bankpulse.service;

import com.usfq.bankpulse.dto.CreatePaymentRequest;
import com.usfq.bankpulse.model.*;
import com.usfq.bankpulse.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.*;

@Service
public class PaymentService {
  private final AccountRepository accounts;
  private final PaymentRepository payments;
  private final AuditEventRepository audits;

  public PaymentService(AccountRepository accounts, PaymentRepository payments, AuditEventRepository audits) {
    this.accounts = accounts; this.payments = payments; this.audits = audits;
  }

  public List<Account> accounts(String username) {
    return accounts.findByOwnerUsernameOrderByAccountNumber(username);
  }

  public List<Payment> payments(String username) {
    return payments.findBySourceAccountOwnerUsernameOrderByCreatedAtDesc(username);
  }

  public Optional<Payment> payment(Long id, String username) {
    return payments.findByIdAndSourceAccountOwnerUsername(id, username);
  }

  @Transactional
  public Payment create(CreatePaymentRequest req, String username) {
    if (req == null || req.sourceAccountId() == null) throw new IllegalArgumentException("Cuenta requerida");
    if (req.beneficiary() == null || req.beneficiary().isBlank()) throw new IllegalArgumentException("Beneficiario requerido");
    if (req.amount() == null || req.amount().signum() <= 0) throw new IllegalArgumentException("El monto debe ser mayor que cero");

    Account account = accounts.findOwnedForUpdate(req.sourceAccountId(), username)
      .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada o no pertenece al usuario autenticado"));

    if (account.getBalance().compareTo(req.amount()) < 0) throw new IllegalArgumentException("Saldo insuficiente");
    account.setBalance(account.getBalance().subtract(req.amount()));

    OffsetDateTime now = OffsetDateTime.now();
    Payment payment = new Payment();
    payment.setReference("BP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
    payment.setSourceAccount(account);
    payment.setBeneficiary(req.beneficiary().trim());
    payment.setAmount(req.amount());
    payment.setStatus(PaymentStatus.COMPLETED);
    payment.setCreatedAt(now);
    payment.setUpdatedAt(now);

    accounts.save(account);
    payment = payments.save(payment);
    audits.save(new AuditEvent(username, "PAYMENT_CREATED", "Payment", payment.getId().toString(), now));
    return payment;
  }
}
