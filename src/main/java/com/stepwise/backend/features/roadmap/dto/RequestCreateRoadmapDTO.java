package com.stepwise.backend.features.roadmap.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record RequestCreateRoadmapDTO(
    @NotEmpty(message = "Professional background is required") String professionalBackground,
    @NotEmpty(message = "Goal field is required") String goal) {

}
