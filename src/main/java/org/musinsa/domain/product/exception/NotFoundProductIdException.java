package org.musinsa.domain.product.exception;

import org.musinsa.global.exception.OrderInputException;

/**
 * 상품 번호 오류 exception
 */
public class NotFoundProductIdException extends OrderInputException {
    public NotFoundProductIdException(String message) {
        super(message);
    }
}
