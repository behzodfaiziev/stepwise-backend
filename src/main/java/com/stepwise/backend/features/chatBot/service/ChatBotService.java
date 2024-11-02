package com.stepwise.backend.features.chatBot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stepwise.backend.features.chatBot.dto.ChatBotMessageResponse;
import com.stepwise.backend.features.chatBot.dto.ChatBotPromptRequest;

public interface ChatBotService {

  ChatBotMessageResponse replyToPrompt(ChatBotPromptRequest prompt) throws JsonProcessingException;
}
