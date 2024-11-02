package com.stepwise.backend.features.roadmap.repo;

import com.stepwise.backend.features.roadmap.entity.PhaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhaseRepo extends JpaRepository<PhaseEntity, Integer> {

}
