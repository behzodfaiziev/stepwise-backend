package com.stepwise.backend.features.jobApply.dto;

import lombok.Builder;

@Builder
public record JobApplyResponse(String message, Integer sessionId) {

}
