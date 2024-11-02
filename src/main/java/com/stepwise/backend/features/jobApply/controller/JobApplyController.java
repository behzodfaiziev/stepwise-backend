package com.stepwise.backend.features.jobApply.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stepwise.backend.features.jobApply.dto.JobApplyRequest;
import com.stepwise.backend.features.jobApply.dto.JobApplyResponse;
import com.stepwise.backend.features.jobApply.service.JobApplyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/job-apply")
public class JobApplyController {

  public JobApplyController(JobApplyService jobApplyService) {
    this.jobApplyService = jobApplyService;
  }

  private final JobApplyService jobApplyService;

  @PostMapping("preparation/interview")
  public JobApplyResponse prepareForInterview(
      @Valid
      @RequestBody
      JobApplyRequest jobApplyRequest) throws JsonProcessingException {
    return jobApplyService.prepareForInterview(jobApplyRequest);
  }

  @PostMapping("preparation/language-skill")
  public JobApplyResponse prepareForLanguageSkill(
      @Valid
      @RequestBody
      JobApplyRequest jobApplyRequest) throws JsonProcessingException {
    return jobApplyService.prepareForLanguageSkill(jobApplyRequest);
  }

//  @PostMapping("preparation/skill-gap-analysis")
//  public JobApplyResponse analyzeSkillGap(
//      @Valid
//      @RequestBody
//      JobApplyRequest jobApplyRequest) throws JsonProcessingException {
//    return jobApplyService.analyzeSkillGap(jobApplyRequest);
//  }

  @PostMapping("tool/resume")
  public JobApplyResponse resumeBuilder(
      @Valid
      @RequestBody
      JobApplyRequest jobApplyRequest) throws JsonProcessingException {
    return jobApplyService.resumeBuilder(jobApplyRequest);
  }

  @PostMapping("tool/cover-letter")
  public JobApplyResponse coverLetterBuilder(
      @Valid
      @RequestBody
      JobApplyRequest jobApplyRequest) throws JsonProcessingException {
    return jobApplyService.coverLetterBuilder(jobApplyRequest);
  }

  @PostMapping("tool/email-response")
  public JobApplyResponse emailResponseBuilder(
      @Valid
      @RequestBody
      JobApplyRequest jobApplyRequest) throws JsonProcessingException {
    return jobApplyService.emailResponseBuilder(jobApplyRequest);
  }

  @PostMapping("tool/write-caption")
  public JobApplyResponse writeCaptionBuilder(
      @Valid
      @RequestBody
      JobApplyRequest jobApplyRequest) throws JsonProcessingException {
    return jobApplyService.writeCaptionBuilder(jobApplyRequest);
  }
}
