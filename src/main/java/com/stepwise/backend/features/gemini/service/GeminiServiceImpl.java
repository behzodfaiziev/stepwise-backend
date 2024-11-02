package com.stepwise.backend.features.gemini.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.stepwise.backend.features.roadmap.repo.RoadmapRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiServiceImpl implements GeminiService {

  public GeminiServiceImpl(RestTemplate restTemplate, RoadmapRepo roadmapRepo) {
    this.restTemplate = restTemplate;
    this.roadmapRepo = roadmapRepo;
  }

  @Value("${GEMINI_API_KEY}")
  private String geminiApiKey;

  private final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro:generateContent?key=%s";

  private final RestTemplate restTemplate;

  private final RoadmapRepo roadmapRepo;

  @Override
  public String replyToPrompt(String prompt) {
    String apiUrl = String.format(API_URL, geminiApiKey);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode requestBody = objectMapper.createObjectNode();
    requestBody
        .putArray("contents")
        .addObject()
        .putArray("parts")
        .addObject()
        .put("text", prompt);

    HttpEntity<ObjectNode> request = new HttpEntity<>(requestBody, headers);

    try {
      ResponseEntity<ObjectNode> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request,
          ObjectNode.class);

      if (response.getStatusCode().isError()) {
        throw new RuntimeException("Error calling Gemini API: " + response.getStatusCode());
      }

      return getResult(response.getBody());

    } catch (Exception e) {
      System.out.println("Error calling Gemini API: " + e.getMessage());
      throw new RuntimeException("Error calling Gemini API" + e.getMessage());
    }
  }

  private String getResult(ObjectNode responseBody) {
    if (responseBody == null) {
      throw new RuntimeException("Response is null");
    }

    if (responseBody.isEmpty()) {
      throw new RuntimeException("Response is empty");
    }

    if (!responseBody.has("candidates")) {
      throw new RuntimeException("Missing 'candidates' in responseBody");
    }

    JsonNode candidates = responseBody.get("candidates");
    if (candidates.isEmpty() || !candidates.get(0).has("content")) {
      throw new RuntimeException("Missing 'content' in the first candidate");
    }

    JsonNode content = candidates.get(0).get("content");
    if (!content.has("parts") || content.get("parts").isEmpty()) {
      throw new RuntimeException("Missing 'parts' in content");
    }

    JsonNode parts = content.get("parts").get(0);
    if (!parts.has("text")) {
      throw new RuntimeException("Missing 'text' in parts");
    }

    String text = parts.get("text").asText();

    // Remove leading and trailing backticks
    text = text.replaceAll("^```json|```$", "").trim();

    return text;
  }
}
