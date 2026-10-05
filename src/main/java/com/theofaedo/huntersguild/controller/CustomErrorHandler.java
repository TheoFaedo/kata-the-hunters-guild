package com.theofaedo.huntersguild.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.theofaedo.huntersguild.dto.ErrorResponse;
import com.theofaedo.huntersguild.exception.ConflictException;
import com.theofaedo.huntersguild.exception.ForbiddenException;
import com.theofaedo.huntersguild.exception.NotFoundException;

@ControllerAdvice
public class CustomErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException e) {
        final ErrorResponse error = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(error, HttpStatus.valueOf(error.status()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException e) {
        final ErrorResponse error = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT);

        return new ResponseEntity<>(error, HttpStatus.valueOf(error.status()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(ForbiddenException e) {
        final ErrorResponse error = new ErrorResponse(e.getMessage(), HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(error, HttpStatus.valueOf(error.status()));
    }

}
