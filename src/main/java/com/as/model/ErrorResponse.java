package com.as.model;

import lombok.Builder;

@Builder(setterPrefix = "with")
public class ErrorResponse {
    String message;
}
