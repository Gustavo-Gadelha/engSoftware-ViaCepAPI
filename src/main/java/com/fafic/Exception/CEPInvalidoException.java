package com.fafic.Exception;

public class CEPInvalidoException extends RuntimeException {
    public CEPInvalidoException() {
        super();
    }

    public CEPInvalidoException(String message) {
        super(message);
    }
}
