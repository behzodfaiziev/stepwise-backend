package com.stepwise.backend.features.mentalRoadmap.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stepwise.backend.features.mentalRoadmap.entity.MentalRoadmapEntity;
import java.util.List;

public interface MentalRoadmapService {

  List<MentalRoadmapEntity> createMentalRoadmap(String mood) throws JsonProcessingException;

  List<MentalRoadmapEntity>  getMentalRoadmap();
}
