package com.springcloud.aws.examples.exception;

public class RetriableException extends RuntimeException {

    public RetriableException(String message) {
        super(message);
    }
}
