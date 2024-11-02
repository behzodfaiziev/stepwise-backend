package com.stepwise.backend.features.roadmap.repo;

import com.stepwise.backend.features.roadmap.entity.RoadmapResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadmapResourceRepo extends JpaRepository<RoadmapResourceEntity, Integer> {

}
