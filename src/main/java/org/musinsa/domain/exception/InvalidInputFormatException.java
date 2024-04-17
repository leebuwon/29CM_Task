package org.musinsa.domain.exception;

import org.musinsa.global.exception.OrderInputException;

public class InvalidInputFormatException extends OrderInputException {
    public InvalidInputFormatException(String message) {
        super(message);
    }
}
