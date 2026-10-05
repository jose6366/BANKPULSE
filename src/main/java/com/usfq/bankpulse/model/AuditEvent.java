package com.usfq.bankpulse.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "audit_events")
public class AuditEvent {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String username;
  @Column(nullable = false)
  private String action;
  @Column(nullable = false)
  private String entityType;
  private String entityId;
  @Column(nullable = false)
  private OffsetDateTime occurredAt;

  public AuditEvent() {}
  public AuditEvent(String username, String action, String entityType, String entityId, OffsetDateTime occurredAt) {
    this.username = username; this.action = action; this.entityType = entityType; this.entityId = entityId; this.occurredAt = occurredAt;
  }
  public Long getId() { return id; }
  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }
  public String getAction() { return action; }
  public void setAction(String action) { this.action = action; }
  public String getEntityType() { return entityType; }
  public void setEntityType(String entityType) { this.entityType = entityType; }
  public String getEntityId() { return entityId; }
  public void setEntityId(String entityId) { this.entityId = entityId; }
  public OffsetDateTime getOccurredAt() { return occurredAt; }
  public void setOccurredAt(OffsetDateTime occurredAt) { this.occurredAt = occurredAt; }
}
