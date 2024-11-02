package com.stepwise.backend.features.mentalRoadmap.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stepwise.backend.features.mentalRoadmap.entity.MentalRoadmapEntity;
import com.stepwise.backend.features.mentalRoadmap.service.MentalRoadmapService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mental-roadmap")
public class MentalRoadmapController {

  public MentalRoadmapController(MentalRoadmapService mentalRoadmapService) {
    this.mentalRoadmapService = mentalRoadmapService;
  }

  private final MentalRoadmapService mentalRoadmapService;

  @PostMapping("{mood}")
  public List<MentalRoadmapEntity> createMentalRoadmap(@PathVariable String mood)
      throws JsonProcessingException {
    return mentalRoadmapService.createMentalRoadmap(mood);
  }

  @GetMapping
  public List<MentalRoadmapEntity>  getMentalRoadmap() {
    return mentalRoadmapService.getMentalRoadmap();
  }

}
