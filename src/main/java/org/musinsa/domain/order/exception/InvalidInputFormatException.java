package org.musinsa.domain.order.exception;

import org.musinsa.global.exception.OrderInputException;

public class InvalidInputFormatException extends OrderInputException {
    public InvalidInputFormatException(String message) {
        super(message);
    }
}
