package org.gerasic.exceptions;

public class SameLoginException extends RuntimeException {
    public SameLoginException(String message) {
        super(message);
    }

    public SameLoginException(String message, Throwable cause) {}
}

