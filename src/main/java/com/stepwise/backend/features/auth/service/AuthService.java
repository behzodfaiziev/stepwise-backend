package com.stepwise.backend.features.auth.service;


import com.stepwise.backend.features.auth.dto.SignInRequestDTO;
import com.stepwise.backend.features.auth.dto.SignInResponseDTO;
import com.stepwise.backend.features.auth.dto.SignUpRequestDTO;
import com.stepwise.backend.features.auth.dto.SignUpResponseDTO;

public interface AuthService {

  SignUpResponseDTO signUp(SignUpRequestDTO request);

  SignInResponseDTO signIn(SignInRequestDTO request);
}
