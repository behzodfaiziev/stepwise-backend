package com.stepwise.backend.features.jobApply.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stepwise.backend.features.jobApply.dto.JobApplyRequest;
import com.stepwise.backend.features.jobApply.dto.JobApplyResponse;

public interface JobApplyService {

  JobApplyResponse prepareForInterview(JobApplyRequest jobApplyRequest)
      throws JsonProcessingException;

  JobApplyResponse prepareForLanguageSkill(JobApplyRequest jobApplyRequest)
      throws JsonProcessingException;

//  JobApplyResponse analyzeSkillGap(JobApplyRequest jobApplyRequest) throws JsonProcessingException;

  JobApplyResponse resumeBuilder(JobApplyRequest jobApplyRequest) throws JsonProcessingException;

  JobApplyResponse coverLetterBuilder(JobApplyRequest jobApplyRequest)
      throws JsonProcessingException;

  JobApplyResponse emailResponseBuilder(JobApplyRequest jobApplyRequest)
      throws JsonProcessingException;

  JobApplyResponse writeCaptionBuilder(JobApplyRequest jobApplyRequest)
      throws JsonProcessingException;
}
