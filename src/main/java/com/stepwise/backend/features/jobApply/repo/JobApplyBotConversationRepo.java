package com.stepwise.backend.features.jobApply.repo;

import com.stepwise.backend.features.jobApply.entity.JobApplyBotConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplyBotConversationRepo extends
    JpaRepository<JobApplyBotConversationEntity, Integer> {

}
