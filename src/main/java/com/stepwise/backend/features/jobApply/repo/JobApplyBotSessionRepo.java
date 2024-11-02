package com.stepwise.backend.features.jobApply.repo;

import com.stepwise.backend.features.jobApply.entity.JobApplyBotSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplyBotSessionRepo extends
    JpaRepository<JobApplyBotSessionEntity, Integer> {

}
