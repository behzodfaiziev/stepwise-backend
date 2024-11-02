package com.stepwise.backend.features.chatBot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stepwise.backend.features.chatBot.dto.ChatBotMessageResponse;
import com.stepwise.backend.features.chatBot.dto.ChatBotPromptRequest;
import com.stepwise.backend.features.chatBot.entity.ChatBotConversationEntity;
import com.stepwise.backend.features.chatBot.entity.ChatBotSessionEntity;
import com.stepwise.backend.features.chatBot.repo.ChatBotConversationRepo;
import com.stepwise.backend.features.chatBot.repo.ChatBotSessionRepo;
import com.stepwise.backend.features.gemini.service.GeminiService;
import com.stepwise.backend.features.user.entity.UserEntity;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatBotServiceImpl implements ChatBotService {

  public ChatBotServiceImpl(GeminiService geminiService, ChatBotSessionRepo chatBotSessionRepo,
      ChatBotConversationRepo chatBotConversationRepo) {
    this.geminiService = geminiService;
    this.chatBotSessionRepo = chatBotSessionRepo;
    this.chatBotConversationRepo = chatBotConversationRepo;
  }

  private final GeminiService geminiService;
  private final ChatBotSessionRepo chatBotSessionRepo;
  private final ChatBotConversationRepo chatBotConversationRepo;

  @Override
  @Transactional
  public ChatBotMessageResponse replyToPrompt(ChatBotPromptRequest prompt)
      throws JsonProcessingException {
//    return ChatBotMessageResponse.builder()
//        .initialTopic("Initial Topic").message("Initial Message")
//        .sessionId(1L).build();
        if (prompt.sessionId() == null) {
          return initialSession(prompt); //
          }
        return replyToExistingSession(prompt);
  }

  @Transactional
  private ChatBotMessageResponse initialSession(ChatBotPromptRequest promptRequest)
      throws JsonProcessingException {
    final String initialPrompt = getInitialChatBotPrompt(promptRequest.prompt());

    final String generatedMessagePrompt = geminiService.replyToPrompt(initialPrompt);

    ObjectMapper mapper = new ObjectMapper();
    // Deserialize the JSON string into GeneratedRoadmapDTO object
    final ChatBotMessageResponse generatedMessage = mapper.readValue(generatedMessagePrompt,
        ChatBotMessageResponse.class);
    return saveInitialChatBotSession(generatedMessage, promptRequest.prompt());
  }

  private ChatBotMessageResponse replyToExistingSession(ChatBotPromptRequest prompt)
      throws JsonProcessingException {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity currentUser = (UserEntity) auth.getPrincipal();
    final ChatBotSessionEntity chatBotSession = chatBotSessionRepo.findById(prompt.sessionId())
        .orElseThrow(() -> new IllegalArgumentException("Session not found"));

    if (!chatBotSession.getUser().getId().equals(currentUser.getId())) {
      throw new IllegalArgumentException("Session not found");
    }

    final List<ChatBotConversationEntity> conversations = chatBotSession.getConversations();

    final String promptString = getChatBotPrompt(prompt.prompt(), conversations);

    final String generatedMessagePrompt = geminiService.replyToPrompt(promptString);

    ObjectMapper mapper = new ObjectMapper();
    // Deserialize the JSON string into GeneratedRoadmapDTO object
    final ChatBotMessageResponse generatedMessage = mapper.readValue(generatedMessagePrompt,
        ChatBotMessageResponse.class);

    final Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    ChatBotConversationEntity chatBotConversationEntity = ChatBotConversationEntity.builder()
        .prompt(prompt.prompt())
        .response(generatedMessage.message())
        .chatBotSession(chatBotSession)
        .createdAt(createdAt)
        .build();

    chatBotConversationRepo.save(chatBotConversationEntity);

    return ChatBotMessageResponse.builder()
        .sessionId(chatBotSession.getId())
        .initialTopic(chatBotSession.getInitialTopic())
        .message(generatedMessage.message())
        .build();
  }


  private String getInitialChatBotPrompt(String initialPrompt) {
    return "The user has asked: " + initialPrompt + ". Keep the answer short and concise. "
        + "Also provide initial information about the topic. "
        + "The answer should be in JSON format and will be used in Spring Boot for parsing,"
        + " consistent with the ChatBotConversationEntity model: "
        + ChatBotMessageResponse.JSON_EXAMPLE;
  }

  private String getChatBotPrompt(String prompt, List<ChatBotConversationEntity> conversations) {
    StringBuilder previousConversations = new StringBuilder();
    for (ChatBotConversationEntity conversation : conversations) {
      previousConversations.append("Prompt: ")
          .append(conversation.getPrompt())
          .append(". Response: ")
          .append(conversation.getResponse())
          .append("\n");
    }

    return "The user has asked: " + prompt + ". Keep the answer short and concise. "
        + "Here is the conversation so far: " + previousConversations
        + "The answer should be in JSON format and will be used in Spring Boot for parsing,"
        + " consistent with the ChatBotConversationEntity model: "
        + ChatBotMessageResponse.JSON_EXAMPLE;
  }

  @Transactional
  private ChatBotMessageResponse saveInitialChatBotSession(ChatBotMessageResponse responseMessage,
      String prompt) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity currentUser = (UserEntity) auth.getPrincipal();

    final Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    /// Create a new Session
    final ChatBotSessionEntity chatBotSessionEntity = ChatBotSessionEntity.builder()
        .initialTopic(responseMessage.initialTopic())
        .user(currentUser)
        .createdAt(createdAt)
        .build();

    final ChatBotSessionEntity savedSession = chatBotSessionRepo.save(chatBotSessionEntity);

    ChatBotConversationEntity chatBotConversationEntity = ChatBotConversationEntity.builder()
        .prompt(prompt)
        .response(responseMessage.message())
        .chatBotSession(savedSession)
        .createdAt(createdAt)
        .build();

    chatBotConversationRepo.save(chatBotConversationEntity);

    return ChatBotMessageResponse.builder()
        .sessionId(savedSession.getId())
        .initialTopic(responseMessage.initialTopic())
        .message(responseMessage.message())
        .build();
  }

}
