package com.stepwise.backend.features.roadmap.repo;

import com.stepwise.backend.features.roadmap.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepo extends JpaRepository<TopicEntity, Integer> {

}
