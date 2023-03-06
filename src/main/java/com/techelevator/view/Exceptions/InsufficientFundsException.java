package com.techelevator.view.Exceptions;

public class InsufficientFundsException extends Exception {

    public InsufficientFundsException() {
        super();
    }

    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(String message, Exception cause) {
        super(message, cause);
    }

}
