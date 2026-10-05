package com.theofaedo.huntersguild.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, LocalDateTime timestamp, int status) {

    public ErrorResponse(String message, HttpStatus status) {
        this(message, LocalDateTime.now(), status.value());
    }

}
