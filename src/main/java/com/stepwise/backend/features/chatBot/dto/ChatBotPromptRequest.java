package com.stepwise.backend.features.chatBot.dto;

import jakarta.validation.constraints.NotEmpty;

public record ChatBotPromptRequest(
    @NotEmpty(message = "Prompt cannot be empty")
    String prompt, Integer sessionId) {

}
