package com.usfq.bankpulse.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "payments")
public class Payment {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true)
  private String reference;
  @ManyToOne(optional = false)
  private Account sourceAccount;
  @Column(nullable = false)
  private String beneficiary;
  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal amount;
  @Enumerated(EnumType.STRING) @Column(nullable = false)
  private PaymentStatus status;
  @Column(nullable = false)
  private OffsetDateTime createdAt;
  @Column(nullable = false)
  private OffsetDateTime updatedAt;

  public Payment() {}
  public Long getId() { return id; }
  public String getReference() { return reference; }
  public void setReference(String reference) { this.reference = reference; }
  public Account getSourceAccount() { return sourceAccount; }
  public void setSourceAccount(Account sourceAccount) { this.sourceAccount = sourceAccount; }
  public String getBeneficiary() { return beneficiary; }
  public void setBeneficiary(String beneficiary) { this.beneficiary = beneficiary; }
  public BigDecimal getAmount() { return amount; }
  public void setAmount(BigDecimal amount) { this.amount = amount; }
  public PaymentStatus getStatus() { return status; }
  public void setStatus(PaymentStatus status) { this.status = status; }
  public OffsetDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
  public OffsetDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
