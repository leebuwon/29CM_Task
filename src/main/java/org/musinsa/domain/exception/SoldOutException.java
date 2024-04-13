package org.musinsa.domain.exception;

/**
 * SoldOutException : 재고 수량보다 주문 수량이 많을 경우 exception 발생
 */
public class SoldOutException extends RuntimeException {
    public SoldOutException(String message) {
        super(message);
    }
}

