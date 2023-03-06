package com.techelevator.view.Exceptions;

public class InvalidSlotIdException extends Exception {
    public InvalidSlotIdException() {
    }

    public InvalidSlotIdException(String message) {
        super(message);
    }

    public InvalidSlotIdException(String message, Exception cause) {
        super(message, cause);
    }
}
