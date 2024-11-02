package com.stepwise.backend.features.chatBot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stepwise.backend.features.chatBot.dto.ChatBotMessageResponse;
import com.stepwise.backend.features.chatBot.dto.ChatBotPromptRequest;
import com.stepwise.backend.features.chatBot.service.ChatBotService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat-bot")
public class ChatBotController {

  public ChatBotController(ChatBotService chatBotService) {
    this.chatBotService = chatBotService;
  }

  private final ChatBotService chatBotService;

  @PostMapping("/chat")
  public ChatBotMessageResponse chatPrompt(
      @RequestBody
      @Valid
      ChatBotPromptRequest prompt) throws JsonProcessingException {
    return chatBotService.replyToPrompt(prompt);
  }

}
