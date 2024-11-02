package com.stepwise.backend.features.roadmap.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stepwise.backend.features.gemini.service.GeminiService;
import com.stepwise.backend.features.roadmap.dto.RequestCreateRoadmapDTO;
import com.stepwise.backend.features.roadmap.dto.RoadmapWrapperDTO;
import com.stepwise.backend.features.roadmap.entity.PhaseEntity;
import com.stepwise.backend.features.roadmap.entity.RoadmapEntity;
import com.stepwise.backend.features.roadmap.entity.RoadmapResourceEntity;
import com.stepwise.backend.features.roadmap.entity.TopicEntity;
import com.stepwise.backend.features.roadmap.repo.PhaseRepo;
import com.stepwise.backend.features.roadmap.repo.RoadmapRepo;
import com.stepwise.backend.features.roadmap.repo.RoadmapResourceRepo;
import com.stepwise.backend.features.roadmap.repo.TopicRepo;
import com.stepwise.backend.features.user.entity.UserEntity;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RoadmapServiceImpl implements RoadmapService {
final Logger logger = LoggerFactory.getLogger(RoadmapServiceImpl.class);
  public RoadmapServiceImpl(GeminiService geminiService, RoadmapRepo roadmapRepo,
      PhaseRepo phaseRepo, TopicRepo topicRepo, RoadmapResourceRepo resourceRepo) {
    this.geminiService = geminiService;
    this.roadmapRepo = roadmapRepo;
    this.phaseRepo = phaseRepo;
    this.topicRepo = topicRepo;
    this.resourceRepo = resourceRepo;
  }

  private final GeminiService geminiService;
  private final RoadmapRepo roadmapRepo;
  private final PhaseRepo phaseRepo;
  private final TopicRepo topicRepo;
  private final RoadmapResourceRepo resourceRepo;

  @Override
  public void generateAndSaveRoadmap(RequestCreateRoadmapDTO roadmap)
      throws JsonProcessingException {

    logger.warn("Generating and saving roadmap");
    final String prompt = getRoadmapContent(roadmap.professionalBackground(),
        roadmap.goal());

    logger.warn("Prompt: {}", prompt);
    final String generatedRoadmapString = geminiService.replyToPrompt(prompt);
    logger.warn("Generated roadmap: {}", generatedRoadmapString);
    ObjectMapper mapper = new ObjectMapper();
    // Deserialize the JSON string into GeneratedRoadmapDTO object
    final RoadmapWrapperDTO generatedRoadmap = mapper.readValue(generatedRoadmapString,
        RoadmapWrapperDTO.class);

    logger.warn("Saving roadmap");
    saveRoadmap(generatedRoadmap.getRoadmap());
  }

  private void saveRoadmap(RoadmapEntity generatedRoadmap) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity currentUser = (UserEntity) auth.getPrincipal();

    logger.warn("Saving roadmap for user: {}", currentUser.getUsername());
    final RoadmapEntity roadmapToSave = RoadmapEntity.builder()
        .title(generatedRoadmap.getTitle())
        .description(generatedRoadmap.getDescription())
        .user(currentUser)
        .build();

    /// Save the RoadmapEntity
    final RoadmapEntity savedRoadmap = roadmapRepo.save(roadmapToSave);

    // Iterate over each PhaseEntity, set the roadmap reference, and save each PhaseEntity
    for (PhaseEntity phase : generatedRoadmap.getPhases()) {

      final PhaseEntity phaseToSave = PhaseEntity.builder()
          .title(phase.getTitle())
          .duration(phase.getDuration())
          .imageUrl(phase.getImageUrl())
          .roadmap(savedRoadmap)
          .build();

      // Save the PhaseEntity
      final PhaseEntity savedPhase = phaseRepo.save(phaseToSave);

      // Iterate over each TopicEntity in the PhaseEntity, set the phase reference, and save each TopicEntity
      for (TopicEntity topic : phase.getTopics()) {
        final TopicEntity topicToSave = TopicEntity.builder()
            .title(topic.getTitle())
            .description(topic.getDescription())
            .phase(savedPhase)
            .build();

        // Save the TopicEntity
        final TopicEntity savedTopic = topicRepo.save(topicToSave);

        // Iterate over each RoadmapResourceEntity in the TopicEntity, set the topic reference, and save each RoadmapResourceEntity
        for (RoadmapResourceEntity resource : topic.getResources()) {
          final RoadmapResourceEntity resourceToSave = RoadmapResourceEntity.builder()
              .title(resource.getTitle())
              .description(resource.getDescription())
              .type(resource.getType())
              .link(resource.getLink())
              .provider(resource.getProvider())
              .topic(savedTopic)
              .build();

          // Save the RoadmapResourceEntity
          resourceRepo.save(resourceToSave);
        }
      }

    }

    logger.warn("Roadmap saved successfully");
  }

  private String getRoadmapContent(String professionalBackground, String goal) {
    return "I would like to create a personalized learning roadmap for a user based on their "
        + "professional background and their specific learning goal. "
        + "Please use the following information to generate a detailed and structured roadmap: "
        + "Professional Background: " + professionalBackground + " " + "Learning Goal: " + goal
        + " " + "Requirements for the Roadmap: "
        + "Suggest a variety of high-quality resources such as online courses, "
        + "academic papers, articles, tutorials, and videos. Note: videos should be from Youtube, "
        + "it would be great if you provide playlist of each topic. "
        + "Ensure that these resources "
        + "cover each general topic comprehensively and are from reputable sources."
        + "Include links to these resources and mention any prerequisites for each. "
        + "Step-by-Step Plan Categorized by General Topics: "
        + "Provide a detailed step-by-step plan that is categorized by general "
        + "topics relevant to the learning goal. Break down the learning process into "
        + "these categories and specify the duration for each phase. "
        + "The answer should be in JSON format and will be used in Spring Boot for parsing, "
        + "give 3 phases, 3 topics and 2 resources for each topic. "
        + " consistent with the RoadmapWrapperDTO model: " + RoadmapWrapperDTO.JSON_EXAMPLE;
  }

  @Override
  public void deleteRoadmap(Integer id) {
    roadmapRepo.deleteById(id);
  }

  @Override
  public RoadmapEntity getRoadmap() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity currentUser = (UserEntity) auth.getPrincipal();

    final List<RoadmapEntity> roadmaps = roadmapRepo.findByUserId(
        Math.toIntExact(currentUser.getId()));

    return roadmaps.get(roadmaps.size() - 1);
  }
}
