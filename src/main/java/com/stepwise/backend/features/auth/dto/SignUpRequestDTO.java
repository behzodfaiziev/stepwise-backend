package com.stepwise.backend.features.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequestDTO {
  @NotEmpty(message = "Email is required")
  private final String email;
  @NotEmpty(message = "Password is required")
  private final String password;
  @NotEmpty(message = "Full name is required")
  private final String fullName;
}
