package com.stepwise.backend.features.roadmap.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stepwise.backend.features.roadmap.dto.RequestCreateRoadmapDTO;
import com.stepwise.backend.features.roadmap.entity.RoadmapEntity;

public interface RoadmapService {

  void generateAndSaveRoadmap(RequestCreateRoadmapDTO roadmap)
      throws JsonProcessingException;

  void deleteRoadmap(Integer id);

  RoadmapEntity getRoadmap();
}
