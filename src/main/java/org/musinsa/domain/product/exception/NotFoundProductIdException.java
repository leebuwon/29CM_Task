package org.musinsa.domain.product.exception;

import org.musinsa.global.exception.OrderInputException;

/**
 * NotFoundProductIdException : 없는 상품 번호 오류 exception 발생
 */
public class NotFoundProductIdException extends OrderInputException {
    public NotFoundProductIdException(String message) {
        super(message);
    }
}
