package com.demoqa.util.exceptions;


/**
 * @author amitaarya
 */
public class NullValueException extends RuntimeException {

    public NullValueException(String message) {
        super(message);
    }

    public NullValueException(String message, Throwable cause) {
        super(message, cause);
    }
}

