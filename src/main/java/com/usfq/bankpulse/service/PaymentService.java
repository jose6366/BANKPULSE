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
  private final AccountRepository accounts; private final PaymentRepository payments; private final AuditEventRepository audits;
  public PaymentService(AccountRepository accounts, PaymentRepository payments, AuditEventRepository audits) { this.accounts=accounts; this.payments=payments; this.audits=audits; }
  public List<Account> accounts(){ return accounts.findAll(); }
  public List<Payment> payments(){ return payments.findAllByOrderByCreatedAtDesc(); }
  public Optional<Payment> payment(Long id){ return payments.findById(id); }
  @Transactional
  public Payment create(CreatePaymentRequest req, String username) {
    Account a=accounts.findById(req.sourceAccountId()).orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
    if(req.amount().signum()<=0) throw new IllegalArgumentException("El monto debe ser mayor que cero");
    if(a.getBalance().compareTo(req.amount())<0) throw new IllegalArgumentException("Saldo insuficiente");
    a.setBalance(a.getBalance().subtract(req.amount()));
    OffsetDateTime now=OffsetDateTime.now();
    Payment p=Payment.builder().reference("BP-"+UUID.randomUUID().toString().substring(0,8).toUpperCase())
      .sourceAccount(a).beneficiary(req.beneficiary().trim()).amount(req.amount()).status(PaymentStatus.COMPLETED)
      .createdAt(now).updatedAt(now).build();
    p=payments.save(p); accounts.save(a);
    audits.save(AuditEvent.builder().username(username).action("PAYMENT_CREATED").entityType("Payment").entityId(p.getId().toString()).occurredAt(now).build());
    return p;
  }
}
