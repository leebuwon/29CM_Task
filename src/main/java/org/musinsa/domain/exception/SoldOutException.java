package org.musinsa.domain.exception;

import org.musinsa.global.exception.GlobalException;

/**
 * SoldOutException : 재고 수량보다 주문 수량이 많을 경우 exception 발생
 */
public class SoldOutException extends GlobalException {
    public SoldOutException(String message) {
        super(message);
    }
}

