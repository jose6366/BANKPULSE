package com.usfq.bankpulse.service;

import com.usfq.bankpulse.dto.CreatePaymentRequest;
import com.usfq.bankpulse.model.Account;
import com.usfq.bankpulse.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceTest {
  @Autowired PaymentService service;
  @Autowired AccountRepository accounts;
  @Autowired PaymentRepository payments;
  @Autowired AuditEventRepository audits;
  private Account demo;

  @BeforeEach
  void setup() {
    audits.deleteAll(); payments.deleteAll(); accounts.deleteAll();
    demo = accounts.save(new Account("001-TEST", "Cliente Test", "demo", new BigDecimal("100.00")));
  }

  @Test
  void createsPaymentDiscountsBalanceAndAudits() {
    var p = service.create(new CreatePaymentRequest(demo.getId(), "Proveedor", new BigDecimal("25.50")), "demo");
    assertNotNull(p.getId());
    assertNotNull(p.getReference());
    assertEquals("Proveedor", p.getBeneficiary());
    assertEquals(0, accounts.findById(demo.getId()).orElseThrow().getBalance().compareTo(new BigDecimal("74.50")));
    assertEquals(1, audits.count());
  }

  @Test
  void rejectsBlankBeneficiary() {
    assertThrows(IllegalArgumentException.class, () -> service.create(new CreatePaymentRequest(demo.getId(), "   ", new BigDecimal("1.00")), "demo"));
  }

  @Test
  void rejectsInsufficientBalance() {
    assertThrows(IllegalArgumentException.class, () -> service.create(new CreatePaymentRequest(demo.getId(), "Proveedor", new BigDecimal("101.00")), "demo"));
  }

  @Test
  void rejectsAccessToAnotherUsersAccount() {
    assertThrows(IllegalArgumentException.class, () -> service.create(new CreatePaymentRequest(demo.getId(), "Proveedor", new BigDecimal("1.00")), "operator"));
  }
}
