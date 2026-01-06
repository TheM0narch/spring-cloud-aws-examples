package com.springcloud.aws.examples.exception;

public class NotRetriableException extends RuntimeException {

    public NotRetriableException(String message) {
        super(message);
    }
}
