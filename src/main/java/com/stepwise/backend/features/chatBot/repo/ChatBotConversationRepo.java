package com.stepwise.backend.features.chatBot.repo;

import com.stepwise.backend.features.chatBot.entity.ChatBotConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatBotConversationRepo extends JpaRepository<ChatBotConversationEntity, Integer> {

}
