package com.stepwise.backend.features.user.repo;

import com.stepwise.backend.features.user.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  Optional<UserEntity> findByEmail(String email);

  boolean existsByEmail(String email);
}
