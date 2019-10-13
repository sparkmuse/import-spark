package com.github.sparkmuse.spark.service.exception;

public class JobAlreadyRunningException extends RuntimeException {

    public JobAlreadyRunningException(String message) {
        super(message);
    }
}
