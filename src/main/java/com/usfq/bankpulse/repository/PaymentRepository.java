package com.usfq.bankpulse.repository;
import com.usfq.bankpulse.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PaymentRepository extends JpaRepository<Payment, Long> { List<Payment> findAllByOrderByCreatedAtDesc(); }
