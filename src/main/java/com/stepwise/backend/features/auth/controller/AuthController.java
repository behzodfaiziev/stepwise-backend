package com.stepwise.backend.features.auth.controller;

import com.stepwise.backend.features.auth.dto.SignInRequestDTO;
import com.stepwise.backend.features.auth.dto.SignInResponseDTO;
import com.stepwise.backend.features.auth.dto.SignUpRequestDTO;
import com.stepwise.backend.features.auth.dto.SignUpResponseDTO;
import com.stepwise.backend.features.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/sign-up")
  public ResponseEntity<SignUpResponseDTO> signUp(
      @Valid
      @RequestBody
      SignUpRequestDTO request) {
    System.out.println("request: " + request);
    return ResponseEntity.ok(authService.signUp(request));
  }

  @PostMapping("/sign-in")
  public ResponseEntity<SignInResponseDTO> signIn(
      @Valid
      @RequestBody
      SignInRequestDTO request) {
    return ResponseEntity.ok(authService.signIn(request));
  }

}
