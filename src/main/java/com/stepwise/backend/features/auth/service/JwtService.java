package com.stepwise.backend.features.auth.service;

import com.stepwise.backend.features.user.entity.UserEntity;
import io.jsonwebtoken.Claims;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {

  String extractUsername(String token);

  <T> T extractClaim(
      String token,
      Function<Claims, T> claimsResolver);

  String generateToken(UserEntity user);

  String generateToken(
      Map<String, Object> extraClaims,
      UserEntity user);

  boolean isTokenValid(
      String token,
      UserDetails userDetails);


}
