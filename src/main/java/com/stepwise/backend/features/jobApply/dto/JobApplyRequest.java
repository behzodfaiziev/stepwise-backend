package com.stepwise.backend.features.jobApply.dto;

public record JobApplyRequest(
    String userPrompt,
    Integer sessionId
) {

}
