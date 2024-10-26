package com.stepwise.backend.features.user.service;

import com.stepwise.backend.features.user.dto.FullNameRequestDTO;
import com.stepwise.backend.features.user.dto.UsagePurposeRequestDTO;
import com.stepwise.backend.features.user.entity.UserEntity;

public interface UserService {

  UserEntity findByEmail(String email);

  UserEntity findById(Integer id);

  void saveUser(UserEntity user);

  void updateFullName(FullNameRequestDTO fullName);

  boolean existsByEmail(String email);

  void updateUserPurpose(UsagePurposeRequestDTO userPurposeRequest);
}
