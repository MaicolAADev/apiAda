package com.example.ada.controller;

import com.example.ada.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public abstract class BaseController {

    protected <T> ResponseEntity<ApiResponse<T>> successResponse(String message, T data) {
        return ResponseEntity.ok(buildResponse(message, data, "success"));
    }

    protected <T> ResponseEntity<ApiResponse<T>> createdResponse(String message, T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(buildResponse(message, data, "success"));
    }

    protected <T> ResponseEntity<ApiResponse<T>> errorResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(buildResponse(message, null, "error"));
    }

    protected ResponseEntity<ApiResponse<Void>> noContentResponse(String message) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(buildResponse(message, null, "success"));
    }

    private <T> ApiResponse<T> buildResponse(String message, T data, String status) {
        return new ApiResponse<>(message, status, LocalDateTime.now(), data);
    }
}
