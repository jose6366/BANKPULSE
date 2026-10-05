package com.usfq.bankpulse.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
@Entity @Table(name="payments") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false, unique=true) private String reference;
  @ManyToOne(optional=false) private Account sourceAccount;
  @Column(nullable=false) private String beneficiary;
  @Column(nullable=false, precision=19, scale=2) private BigDecimal amount;
  @Enumerated(EnumType.STRING) @Column(nullable=false) private PaymentStatus status;
  @Column(nullable=false) private OffsetDateTime createdAt;
  @Column(nullable=false) private OffsetDateTime updatedAt;
}
