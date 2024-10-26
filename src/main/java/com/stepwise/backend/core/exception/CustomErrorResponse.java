package com.stepwise.backend.core.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomErrorResponse(int status, String message) {

}