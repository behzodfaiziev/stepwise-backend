package com.stepwise.backend.features.jobApply.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "job_apply_bot_conversation")
public class JobApplyBotConversationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "job_apply_bot_session_id")
  @JsonIgnore
  private JobApplyBotSessionEntity jobApplyBotSession;

  private String prompt;

  private String response;

  @Column(name = "created_at")
  private Timestamp createdAt;

  public String getConversation() {
    return "Prompt: " + prompt + " Response: " + response;
  }
}
