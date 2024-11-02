package com.stepwise.backend.features.jobApply.enums;

import lombok.Getter;

@Getter
public enum JobApplyType {
  INTERVIEW(10),
  LANGUAGE_SKILL(10),
  RESUME(10),
  COVER_LETTER(10),
  EMAIL_RESPONSE(10),
  WRITE_CAPTION(10);

  private final int limit;

  JobApplyType(int limit) {
    this.limit = limit;
  }

}

