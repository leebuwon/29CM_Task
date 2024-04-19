package org.musinsa.global.exception;

/**
 * OrderInputException : 잘못된 입력으로 발생하며 주문은 계속 이행
 */
public class OrderInputException extends RuntimeException{
    public OrderInputException(String message){
        super(message);
    }
}
