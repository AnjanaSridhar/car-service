package com.as.helpers;

import com.as.model.ErrorResponse;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHelper {
    private static final Gson GSON = new Gson();
    public static ResponseEntity<String> errorMessage(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(GSON.toJson(ErrorResponse.builder().withMessage(message)));
    }
}
