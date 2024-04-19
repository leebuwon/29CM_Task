package org.musinsa.global.exception;

/**
 * GlobalException : GlobalException 발생하면 주문이 종료되고 초기화면으로 이동
 */
public class GlobalException extends RuntimeException{
    public GlobalException(String message){
        super(message);
    }
}
