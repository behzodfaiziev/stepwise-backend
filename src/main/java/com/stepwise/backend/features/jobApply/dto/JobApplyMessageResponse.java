package com.stepwise.backend.features.jobApply.dto;

import lombok.Builder;


@Builder
public record JobApplyMessageResponse(String message, Long sessionId) {

  public static final String JSON_EXAMPLE = """
      {
          "message": "Hello!",
      }""";
}
