package com.stepwise.backend.features.roadmap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stepwise.backend.features.roadmap.entity.RoadmapEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoadmapWrapperDTO {

  @JsonProperty("roadmap")
  private RoadmapEntity roadmap;

  // Example of how the JSON should look like
  public static final String JSON_EXAMPLE = "{\n"
      + "  \"roadmap\": {\n"
      + "    \"title\": \"Example Title\",\n"
      + "    \"description\": \"Example Description\",\n"
      + "    \"phases\": [\n"
      + "      {\n"
      + "        \"title\": \"Phase 1 Title\",\n"
      + "        \"duration\": \"1 month\",\n"
      + "        \"topics\": [\n"
      + "          {\n"
      + "            \"title\": \"Topic 1 Title\",\n"
      + "            \"description\": \"Topic 1 Description\",\n"
      + "            \"resources\": [\n"
      + "              {\n"
      + "                \"type\": \"Book\",\n"
      + "                \"title\": \"Example Book Title\",\n"
      + "                \"link\": \"http://example.com\",\n"
      + "                \"provider\": \"Example Provider\",\n"
      + "                \"description\": \"Example Book Description\",\n"
      + "                \"author\": \"Example Author\"\n"
      + "              }\n"
      + "            ]\n"
      + "          }\n"
      + "        ]\n"
      + "      }\n"
      + "    ]\n"
      + "  }\n"
      + "}";
}
