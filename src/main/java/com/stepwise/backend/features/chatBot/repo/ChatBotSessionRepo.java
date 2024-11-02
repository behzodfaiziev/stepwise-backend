package com.stepwise.backend.features.chatBot.repo;

import com.stepwise.backend.features.chatBot.entity.ChatBotSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatBotSessionRepo extends JpaRepository<ChatBotSessionEntity, Integer> {

}
