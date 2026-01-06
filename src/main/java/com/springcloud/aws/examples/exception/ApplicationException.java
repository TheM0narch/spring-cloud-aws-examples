
package com.springcloud.aws.examples.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
public class ApplicationException extends RuntimeException {

    public ApplicationException(String reason) {
        super(reason);
    }

    public ApplicationException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
