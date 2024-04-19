package org.musinsa.domain.order.exception;

import org.musinsa.global.exception.OrderInputException;

/**
 * InvalidInputFormatException : 상품 번호와 수량이 잘못 입력되었을 경우 exception 발생
 */
public class InvalidInputFormatException extends OrderInputException {
    public InvalidInputFormatException(String message) {
        super(message);
    }
}
