package com.stepwise.backend.features.roadmap.repo;


import com.stepwise.backend.features.roadmap.entity.RoadmapEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadmapRepo extends JpaRepository<RoadmapEntity, Integer> {

  List<RoadmapEntity> findByUserId(Integer userId);
}
