package com.usfq.bankpulse.repository;

import com.usfq.bankpulse.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
  List<Payment> findBySourceAccountOwnerUsernameOrderByCreatedAtDesc(String username);
  Optional<Payment> findByIdAndSourceAccountOwnerUsername(Long id, String username);
}
