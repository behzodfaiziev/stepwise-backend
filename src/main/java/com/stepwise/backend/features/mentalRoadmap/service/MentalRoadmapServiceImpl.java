package com.stepwise.backend.features.mentalRoadmap.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stepwise.backend.features.gemini.service.GeminiService;
import com.stepwise.backend.features.mentalRoadmap.dto.MentalRoadmapConverter;
import com.stepwise.backend.features.mentalRoadmap.entity.MentalRoadmapEntity;
import com.stepwise.backend.features.mentalRoadmap.repo.MentalRoadmapRepo;
import com.stepwise.backend.features.user.entity.UserEntity;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MentalRoadmapServiceImpl implements MentalRoadmapService {

  public MentalRoadmapServiceImpl(MentalRoadmapRepo mentalRoadmapRepo,
      GeminiService geminiService) {
    this.mentalRoadmapRepo = mentalRoadmapRepo;
    this.geminiService = geminiService;
  }

  private final MentalRoadmapRepo mentalRoadmapRepo;
  private final GeminiService geminiService;
  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  @Transactional
  public List<MentalRoadmapEntity> createMentalRoadmap(String mood) throws JsonProcessingException {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity currentUser = (UserEntity) auth.getPrincipal();

//    final List<MentalRoadmapEntity> todayRoadmap = mentalRoadmapRepo.findAllByCreatedAt(
//        Math.toIntExact(currentUser.getId()));

    final String generatedResult = geminiService.replyToPrompt(getPrompt(mood));

    // Deserialize the JSON string into GeneratedRoadmapDTO object
    MentalRoadmapConverter mentalRoadmap = mapper.readValue(generatedResult,
        MentalRoadmapConverter.class);

    List<MentalRoadmapEntity> roadmaps = new ArrayList<>(List.of());

    mentalRoadmap.getRoadmaps().forEach(roadmap -> {
      final MentalRoadmapEntity generatedRoadmap = new MentalRoadmapEntity();
      generatedRoadmap.setTitle(roadmap.getTitle());
      generatedRoadmap.setDescription(roadmap.getDescription());
      generatedRoadmap.setTime(roadmap.getTime());
      generatedRoadmap.setMood(mood);
      generatedRoadmap.setUser(currentUser);
      generatedRoadmap.setCreatedAt(new Timestamp(System.currentTimeMillis()));
      final MentalRoadmapEntity savedMentalRoadmap = mentalRoadmapRepo.save(generatedRoadmap);
      roadmaps.add(savedMentalRoadmap);

    });

    return roadmaps;

  }

  private static String getPrompt(String mood) {
    return "You will be provided with a user's mental state, described as {" + mood + "}. "
        + " Your role is to act as a coach and provide a detailed daily routine designed "
        + "to improve the user's mental state and mood. The routine should include different "
        + "exercises and activities for three parts of the day: morning, midday, and night. "
        + "Each activity should have a title, a brief explanation, and the recommended time "
        + "necessary to complete it. The exercises should focus on addressing the user's "
        + "specific issue and should not be general solutions. The response should be "
        + "structured as a to-do list without full sentences.\n"
        + "For example, the response could look like this:\n" + "\n"
        + "Morning: {title} , {a short what to do} , {time}. Also make sure that, there is only "
        + "1 morning, 1 midday and 1 night. \n"
        + "The answer should be in JSON format and will be used in Spring Boot for parsing,"
        + " consistent with the ChatBotConversationEntity model: "
        + MentalRoadmapConverter.JSON_EXAMPLE;
  }

  @Override
  public List<MentalRoadmapEntity> getMentalRoadmap() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity currentUser = (UserEntity) auth.getPrincipal();

    return mentalRoadmapRepo.findAllByCreatedAt(Math.toIntExact(currentUser.getId()));
  }
}
