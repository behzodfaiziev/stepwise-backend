package com.stepwise.backend.features.auth.dto;

import com.stepwise.backend.features.user.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpResponseDTO {
  private final String token;
  private final UserEntity user;
}
