package com.stepwise.backend.features.user.service;

import com.stepwise.backend.features.user.dto.FullNameRequestDTO;
import com.stepwise.backend.features.user.dto.UsagePurposeRequestDTO;
import com.stepwise.backend.features.user.entity.UserEntity;
import com.stepwise.backend.features.user.enums.UsagePurpose;
import com.stepwise.backend.features.user.repo.UserRepository;
import com.stepwise.backend.product.exceptions.user.UserNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private final UserRepository userRepository;

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  public UserEntity findByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("User not found"));
  }

  public UserEntity findById(Integer id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found"));
  }

  public void saveUser(UserEntity user) {
    userRepository.save(user);
  }

  @Override
  public void updateFullName(FullNameRequestDTO fullName) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity currentUser = (UserEntity) auth.getPrincipal();

    currentUser.setFullName(fullName.fullName());
    saveUser(currentUser);
  }


  @Override
  public void updateUserPurpose(UsagePurposeRequestDTO userPurposeRequest) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity currentUser = (UserEntity) auth.getPrincipal();

    UsagePurpose usagePurpose = UsagePurpose.fromString(userPurposeRequest.purpose());
    currentUser.setUsagePurpose(usagePurpose);

    saveUser(currentUser);
  }
}
