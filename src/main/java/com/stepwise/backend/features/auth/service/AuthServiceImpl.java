package com.stepwise.backend.features.auth.service;

import com.stepwise.backend.features.auth.dto.SignInRequestDTO;
import com.stepwise.backend.features.auth.dto.SignInResponseDTO;
import com.stepwise.backend.features.auth.dto.SignUpRequestDTO;
import com.stepwise.backend.features.auth.dto.SignUpResponseDTO;
import com.stepwise.backend.features.user.entity.UserEntity;
import com.stepwise.backend.features.user.enums.Roles;
import com.stepwise.backend.features.user.enums.UsagePurpose;
import com.stepwise.backend.features.user.service.UserService;
import com.stepwise.backend.product.exceptions.user.EmailAlreadyExistsException;
import java.sql.Timestamp;
import java.time.Instant;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder,
      JwtService jwtService, AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  public SignUpResponseDTO signUp(SignUpRequestDTO request) {

    System.out.println("1");
    if (userService.existsByEmail(request.getEmail())) {
      throw new EmailAlreadyExistsException("This email is already in use.");
    }
    Timestamp currentTimestamp = Timestamp.from(Instant.now());
//    UsagePurpose usagePurpose = UsagePurpose.fromString(request.getPurpose());
    System.out.println("2");
    UserEntity user = UserEntity.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .username("")
        .usagePurpose(UsagePurpose.CREATE)
        .fullName(request.getFullName())
        .createdAt(currentTimestamp)
        .updatedAt(currentTimestamp)
        .role(Roles.SIGNED)
        .build();
    System.out.println("3");
    userService.saveUser(user);

    String jwtToken = jwtService.generateToken(user);
    System.out.println("4");
    return SignUpResponseDTO.builder().token(jwtToken).user(user).build();
  }

  public SignInResponseDTO signIn(SignInRequestDTO request) {

    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    } catch (Exception e) {
      throw new AccessDeniedException("Invalid email or password.");
    }

    UserEntity user = userService.findByEmail(request.getEmail());

    String jwtToken = jwtService.generateToken(user);

    return SignInResponseDTO.builder()
        .token(jwtToken)
        .user(user)
        .build();
  }

}