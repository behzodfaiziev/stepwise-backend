package com.stepwise.backend.features.auth.dto;

import com.stepwise.backend.features.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SignInResponseDTO {

  final String token;

  final UserEntity user;
}
