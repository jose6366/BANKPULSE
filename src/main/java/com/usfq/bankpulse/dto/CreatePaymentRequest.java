package com.usfq.bankpulse.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record CreatePaymentRequest(
  @NotNull Long sourceAccountId,
  @NotBlank String beneficiary,
  @NotNull @DecimalMin(value="0.01") BigDecimal amount
) {}
