package org.musinsa.domain.exception;

/**
 * 상품 번호 오류 exception
 */
public class NotFoundProductIdException extends RuntimeException{
    public NotFoundProductIdException(String message) {
        super(message);
    }
}
