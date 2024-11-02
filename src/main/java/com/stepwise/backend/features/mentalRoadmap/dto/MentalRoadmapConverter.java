package com.stepwise.backend.features.mentalRoadmap.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MentalRoadmapConverter {

  private List<MentalRoadmap> roadmaps;

  @Getter
  public static class MentalRoadmap {
    private String title;
    private String description;
    private String time;
  }

  public static final String JSON_EXAMPLE = """
      {
          "roadmaps": [
          {
              "title": "Title example",
              "description": "Start with a 10-minute mindfulness meditation. Focus on your breath and let go of worries.",
              "time": "Morning example"
          },
          {
              "title": "Title example",
              "description": "Identify your top 3 stressors. Write down actionable steps to tackle them (even small ones!).",
              "time": "Midday example"
          },
          {
              "title": "Title example",
              "description": "Declutter your workspace for 15 minutes. A tidy environment can ease mental clutter",
              "time": "Evening example"
          },
          ]
      }""";
}
