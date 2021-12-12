package com.lhind.application.exception;

public class InvalidDateTimeException extends RuntimeException {
    public InvalidDateTimeException() {
        super();
    }

    public InvalidDateTimeException(String message) {
        super(message);
    }

    public InvalidDateTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDateTimeException(Throwable cause) {
        super(cause);
    }

    protected InvalidDateTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
