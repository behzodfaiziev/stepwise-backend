package com.stepwise.backend.features.jobApply.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stepwise.backend.features.gemini.service.GeminiService;
import com.stepwise.backend.features.jobApply.dto.JobApplyMessageResponse;
import com.stepwise.backend.features.jobApply.dto.JobApplyRequest;
import com.stepwise.backend.features.jobApply.dto.JobApplyResponse;
import com.stepwise.backend.features.jobApply.entity.JobApplyBotConversationEntity;
import com.stepwise.backend.features.jobApply.entity.JobApplyBotSessionEntity;
import com.stepwise.backend.features.jobApply.enums.JobApplyType;
import com.stepwise.backend.features.jobApply.repo.JobApplyBotConversationRepo;
import com.stepwise.backend.features.jobApply.repo.JobApplyBotSessionRepo;
import com.stepwise.backend.features.user.entity.UserEntity;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class JobApplyServiceImpl implements JobApplyService {

  public JobApplyServiceImpl(GeminiService geminiService,
      JobApplyBotSessionRepo jobApplyBotSessionRepo,
      JobApplyBotConversationRepo jobApplyBotConversationRepo, JobApplyPrompts jobApplyPrompts) {
    this.geminiService = geminiService;
    this.jobApplyBotSessionRepo = jobApplyBotSessionRepo;
    this.jobApplyBotConversationRepo = jobApplyBotConversationRepo;
    this.jobApplyPrompts = jobApplyPrompts;
  }

  private final GeminiService geminiService;
  private final JobApplyBotSessionRepo jobApplyBotSessionRepo;
  private final JobApplyBotConversationRepo jobApplyBotConversationRepo;
  private final JobApplyPrompts jobApplyPrompts;

  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public JobApplyResponse prepareForInterview(JobApplyRequest jobApplyRequest)
      throws JsonProcessingException {

    if (jobApplyRequest.sessionId() == null) {

      final String initialPrompt = jobApplyPrompts.interviewPreparation("Flutter Developer");
      return initialSession(initialPrompt, JobApplyType.INTERVIEW);
    }
    return replyToExistingSession(jobApplyRequest, JobApplyType.INTERVIEW);
  }


  @Override
  public JobApplyResponse prepareForLanguageSkill(JobApplyRequest jobApplyRequest)
      throws JsonProcessingException {
    if (jobApplyRequest.sessionId() == null) {
      final String initialPrompt = jobApplyPrompts.languageSkillPreparation("English");
      return initialSession(initialPrompt, JobApplyType.LANGUAGE_SKILL);
    }
    return replyToExistingSession(jobApplyRequest, JobApplyType.LANGUAGE_SKILL);
  }

//  @Override
//  public JobApplyResponse analyzeSkillGap(JobApplyRequest jobApplyRequest)
//      throws JsonProcessingException {
//    if (jobApplyRequest.sessionId() == null) {
//      final String initialPrompt = jobApplyPrompts.skillGapAnalysis();
//      return initialSession(initialPrompt, JobApplyType.SKILL_GAP_ANALYSIS);
//    }
//    return replyToExistingSession(jobApplyRequest, JobApplyType.SKILL_GAP_ANALYSIS);
//  }

  @Override
  public JobApplyResponse resumeBuilder(JobApplyRequest jobApplyRequest)
      throws JsonProcessingException {
    if (jobApplyRequest.sessionId() == null) {
      final String initialPrompt = jobApplyPrompts.resumeBuilderV3();
      return initialSession(initialPrompt, JobApplyType.RESUME);
    }
    return replyToExistingSession(jobApplyRequest, JobApplyType.RESUME);
  }

  @Override
  public JobApplyResponse coverLetterBuilder(JobApplyRequest jobApplyRequest)
      throws JsonProcessingException {
    if (jobApplyRequest.sessionId() == null) {
      final String initialPrompt = jobApplyPrompts.coverLetterBuilderV2();
      return initialSession(initialPrompt, JobApplyType.COVER_LETTER);
    }
    return replyToExistingSession(jobApplyRequest, JobApplyType.COVER_LETTER);
  }

  @Override
  public JobApplyResponse emailResponseBuilder(JobApplyRequest jobApplyRequest)
      throws JsonProcessingException {
    if (jobApplyRequest.sessionId() == null) {
      final String initialPrompt = jobApplyPrompts.emailResponseBuilder();
      return initialSession(initialPrompt, JobApplyType.EMAIL_RESPONSE);
    }
    return replyToExistingSession(jobApplyRequest, JobApplyType.EMAIL_RESPONSE);
  }

  @Override
  public JobApplyResponse writeCaptionBuilder(JobApplyRequest jobApplyRequest)
      throws JsonProcessingException {
    if (jobApplyRequest.sessionId() == null) {
      final String initialPrompt = jobApplyPrompts.writeCaptionBuilder();
      return initialSession(initialPrompt, JobApplyType.WRITE_CAPTION);
    }
    return replyToExistingSession(jobApplyRequest, JobApplyType.WRITE_CAPTION);
  }


  private JobApplyResponse replyToExistingSession(
      JobApplyRequest request,
      JobApplyType jobApplyType)
      throws JsonProcessingException {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity currentUser = (UserEntity) auth.getPrincipal();

    final JobApplyBotSessionEntity jobApplyBotSession = jobApplyBotSessionRepo.findById(
            request.sessionId())
        .orElseThrow(() -> new IllegalArgumentException("Session not found"));

    if (!jobApplyBotSession.getUser().getId().equals(currentUser.getId())) {
      throw new IllegalArgumentException("Session not found");
    }

    final List<JobApplyBotConversationEntity> conversations = jobApplyBotSession.getConversations();
    /// If the limit is set, do not ask more questions and provide the result

    final String promptString = getJobApplyBotPrompt(request.userPrompt(), conversations,
        jobApplyType);

    final String generatedMessagePrompt = geminiService.replyToPrompt(promptString);

    ObjectMapper mapper = new ObjectMapper();
    // Deserialize the JSON string into GeneratedRoadmapDTO object
    final JobApplyMessageResponse generatedMessage = mapper.readValue(generatedMessagePrompt,
        JobApplyMessageResponse.class);

    final Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    JobApplyBotConversationEntity chatBotConversationEntity = JobApplyBotConversationEntity.builder()
        .prompt(request.userPrompt())
        .response(generatedMessage.message())
        .jobApplyBotSession(jobApplyBotSession)
        .createdAt(createdAt)
        .build();

    jobApplyBotConversationRepo.save(chatBotConversationEntity);

    return JobApplyResponse.builder()
        .sessionId(request.sessionId())
        .message(generatedMessage.message())
        .build();
  }

  private JobApplyResponse initialSession(
      String preparedPrompt,
      JobApplyType jobApplyType
  ) throws JsonProcessingException {
    final String generatedMessagePrompt = geminiService.replyToPrompt(preparedPrompt);

    // Deserialize the JSON string into GeneratedRoadmapDTO object
    final JobApplyMessageResponse chatResponse = mapper.readValue(generatedMessagePrompt,
        JobApplyMessageResponse.class);

    return saveInitialJobApplySession(preparedPrompt, chatResponse, jobApplyType);
  }

  private JobApplyResponse saveInitialJobApplySession(
      String prompt,
      JobApplyMessageResponse chatResponse,
      JobApplyType jobApplyType
  ) {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity currentUser = (UserEntity) auth.getPrincipal();

    final Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    /// Create a new Session
    final JobApplyBotSessionEntity jobAppBotSessionEntity = JobApplyBotSessionEntity.builder()
        .user(currentUser)
        .type(String.valueOf(jobApplyType))
        .createdAt(createdAt)
        .build();

    final JobApplyBotSessionEntity savedSession = jobApplyBotSessionRepo.save(
        jobAppBotSessionEntity);

    JobApplyBotConversationEntity chatBotConversationEntity = JobApplyBotConversationEntity.builder()
        .prompt(prompt)
        .response(chatResponse.message())
        .jobApplyBotSession(savedSession)
        .createdAt(createdAt)
        .build();

    jobApplyBotConversationRepo.save(chatBotConversationEntity);

    return JobApplyResponse.builder()
        .sessionId(Math.toIntExact(savedSession.getId()))
        .message(chatResponse.message())
        .build();
  }

  private String getJobApplyBotPrompt(String prompt,
      List<JobApplyBotConversationEntity> conversations, JobApplyType jobApplyType) {
    StringBuilder previousConversations = new StringBuilder();
    for (JobApplyBotConversationEntity conversation : conversations) {
      previousConversations.append("User Response: ")
          .append(conversation.getPrompt())
          .append(". Chat Question: ")
          .append(conversation.getResponse())
          .append("\n");
    }

    if (conversations.size() >= jobApplyType.getLimit()) {
      return
          "Here is the conversation so far: {" + previousConversations
              + "} Here is the user's last answer: {"
              + prompt
              + "}. Stop asking questions, give the result and provide detailed"
              + " answer on the initial requirement. "
              + "The question should be in JSON format and will be used in Spring Boot for parsing, "
              + " consistent with the ChatBotConversationEntity model: "
              + JobApplyMessageResponse.JSON_EXAMPLE;
    }
    return
        "Here is the conversation so far: {" + previousConversations
            + "} Here is the user's answer: {"
            + prompt + ".} "
            + "The question should be in JSON format and will be used in Spring Boot for parsing,"
            + " consistent with the ChatBotConversationEntity model: "
            + JobApplyMessageResponse.JSON_EXAMPLE;
  }
}
