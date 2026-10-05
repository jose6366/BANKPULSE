package com.usfq.bankpulse.controller;

import com.usfq.bankpulse.dto.CreatePaymentRequest;
import com.usfq.bankpulse.model.*;
import com.usfq.bankpulse.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class PaymentRestController {
  private final PaymentService service;
  public PaymentRestController(PaymentService service) { this.service = service; }

  @GetMapping("/accounts")
  public List<Account> accounts(Authentication auth) { return service.accounts(auth.getName()); }

  @GetMapping("/payments")
  public List<Payment> payments(Authentication auth) { return service.payments(auth.getName()); }

  @GetMapping("/payments/{id}")
  public ResponseEntity<Payment> payment(@PathVariable Long id, Authentication auth) {
    return service.payment(id, auth.getName()).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/payments")
  public ResponseEntity<?> create(@Valid @RequestBody CreatePaymentRequest req, Authentication auth) {
    try { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req, auth.getName())); }
    catch (IllegalArgumentException e) { return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); }
  }
}
