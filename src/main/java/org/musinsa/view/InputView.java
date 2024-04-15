package org.musinsa.view;

import lombok.AllArgsConstructor;
import org.musinsa.domain.util.Console;

@AllArgsConstructor
public class InputView {
    private static final String WELCOME_MESSAGE = "입력(o[order]: 주문, q[quit]: 종료): ";
    private static final String TYPE_NO_MATCH_ERROR = "잘못된 입력입니다. 다시 입력해주세요.";
    private static final String EXIT_MESSAGE = "고객님의 주문 감사합니다.";
    private static final String PRODUCT_ID_INPUT_MESSAGE = "상품번호: ";
    private static final String QUANTITY_INPUT_MESSAGE = "수량: ";

    private final Console console;

    public String getInput() {
        return console.getInput(WELCOME_MESSAGE);
    }

    public void displayError() {
        System.out.println(TYPE_NO_MATCH_ERROR);
    }

    public void displayExitMessage() {
        System.out.println(EXIT_MESSAGE);
        console.close();
    }

    public String getProductIDInput() {
        return console.getInput(PRODUCT_ID_INPUT_MESSAGE);
    }

    public String getQuantityInput() {
        return console.getInput(QUANTITY_INPUT_MESSAGE);
    }
}
