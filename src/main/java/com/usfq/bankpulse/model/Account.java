package com.usfq.bankpulse.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true)
  private String accountNumber;
  @Column(nullable = false)
  private String owner;
  @Column(nullable = false)
  private String ownerUsername;
  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal balance;

  public Account() {}
  public Account(String accountNumber, String owner, String ownerUsername, BigDecimal balance) {
    this.accountNumber = accountNumber;
    this.owner = owner;
    this.ownerUsername = ownerUsername;
    this.balance = balance;
  }
  public Long getId() { return id; }
  public String getAccountNumber() { return accountNumber; }
  public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
  public String getOwner() { return owner; }
  public void setOwner(String owner) { this.owner = owner; }
  public String getOwnerUsername() { return ownerUsername; }
  public void setOwnerUsername(String ownerUsername) { this.ownerUsername = ownerUsername; }
  public BigDecimal getBalance() { return balance; }
  public void setBalance(BigDecimal balance) { this.balance = balance; }
}
