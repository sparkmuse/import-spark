package com.github.sparkmuse.spark.controller;

import com.github.sparkmuse.spark.service.exception.JobAlreadyRunningException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ImportJobControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = JobAlreadyRunningException.class)
    protected ResponseEntity<Object> handleJobAlreadyRunningException(JobAlreadyRunningException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}