package com.stepwise.backend.features.roadmap.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roadmap_resource")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoadmapResourceEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "type")
  private String type;

  @Column(name = "title")
  private String title;

  @Column(name = "link")
  private String link;

  @Column(name = "provider")
  private String provider;

  @Column(name = "description")
  private String description;

  @Column(name = "author")
  private String author;

  @ManyToOne
  @JoinColumn(name = "topic_id")
  @JsonIgnore
  private TopicEntity topic;
}