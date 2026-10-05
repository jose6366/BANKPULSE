package com.usfq.bankpulse.repository;
import com.usfq.bankpulse.model.AuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AuditEventRepository extends JpaRepository<AuditEvent, Long> {}
