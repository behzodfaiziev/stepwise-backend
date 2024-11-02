package com.stepwise.backend.features.chatBot.dto;

import lombok.Builder;

@Builder
public record ChatBotMessageResponse(String message, String initialTopic, Long sessionId) {

  public static final String JSON_EXAMPLE = """
      {
          "message": "Hello!",
          "initialTopic": "Introduction"
      }""";
}
