package com.usfq.bankpulse.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
@Entity @Table(name="audit_events") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuditEvent {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false) private String username;
  @Column(nullable=false) private String action;
  @Column(nullable=false) private String entityType;
  private String entityId;
  @Column(nullable=false) private OffsetDateTime occurredAt;
}
