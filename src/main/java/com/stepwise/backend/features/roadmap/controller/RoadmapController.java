package com.stepwise.backend.features.roadmap.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stepwise.backend.features.roadmap.dto.RequestCreateRoadmapDTO;
import com.stepwise.backend.features.roadmap.entity.RoadmapEntity;
import com.stepwise.backend.features.roadmap.service.RoadmapService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roadmap")
public class RoadmapController {

  final RoadmapService roadmapService;

  public RoadmapController(RoadmapService roadmapService) {
    this.roadmapService = roadmapService;
  }

  @GetMapping
  public ResponseEntity<RoadmapEntity> getRoadmap() {
    return ResponseEntity.ok(roadmapService.getRoadmap());
  }

  @PostMapping
  public ResponseEntity<Void> generateRoadmapContent(
      @Valid @RequestBody RequestCreateRoadmapDTO createRoadmapDTO)
      throws JsonProcessingException {
    roadmapService.generateAndSaveRoadmap(createRoadmapDTO);
    return ResponseEntity.ok(null);
  }
}
