package com.stepwise.backend.features.mentalRoadmap.repo;

import com.stepwise.backend.features.mentalRoadmap.entity.MentalRoadmapEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MentalRoadmapRepo extends JpaRepository<MentalRoadmapEntity, Integer> {

  @Query("SELECT m FROM MentalRoadmap m WHERE FUNCTION('DATE', m.createdAt) = CURRENT_DATE AND m.user.id = :userId")
  List<MentalRoadmapEntity> findAllByCreatedAt(@Param("userId") Integer userId);
}