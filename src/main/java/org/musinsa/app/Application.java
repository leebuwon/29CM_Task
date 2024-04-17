package org.musinsa.app;

import org.musinsa.domain.controller.OrderController;
import org.musinsa.domain.entity.Product;
import org.musinsa.global.factory.SingletonFactory;
import org.musinsa.view.InputView;
import org.musinsa.view.ProductListView;

public class Application {
    InputView inputView = SingletonFactory.getInputView();
    ProductListView productListView = SingletonFactory.getProductListView();
    OrderController orderController = SingletonFactory.createOrderController();

    public void run() {
        while (true) {
            String input = inputView.getInput();

            switch (input) {
                case "o" -> {
                    Product[] products = orderController.displayProducts();
                    productListView.displayProducts(products);
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
