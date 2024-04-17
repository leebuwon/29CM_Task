package org.musinsa.app;

import org.musinsa.domain.controller.OrderController;
import org.musinsa.global.factory.SingletonFactory;
import org.musinsa.view.InputView;

public class Application {
    InputView inputView = SingletonFactory.getInputView();
    OrderController orderController = SingletonFactory.createOrderController();

    public void run() {
        while (true) {
            String input = inputView.getInput();

            switch (input) {
                case "o" -> {
                    orderController.displayProduct();
                    orderInput();
                }
                case "q", "quit" -> {
                    inputView.displayExitMessage();
                    return;
                }
                default -> inputView.displayError();
            }
        }
    }


    public void orderInput() {
        while (true){
            String productIdInput = inputView.getProductIDInput();
            String quantityInput = inputView.getQuantityInput();

            if (orderController.processOrder(productIdInput, quantityInput)) break;
        }
    }
}
