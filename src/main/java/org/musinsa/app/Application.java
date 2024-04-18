package org.musinsa.app;

import org.musinsa.domain.controller.OrderController;
import org.musinsa.domain.entity.Order;
import org.musinsa.domain.entity.Product;
import org.musinsa.global.exception.GlobalException;
import org.musinsa.global.exception.OrderInputException;
import org.musinsa.global.factory.SingletonFactory;
import org.musinsa.view.InputView;
import org.musinsa.view.ProductListView;

import java.util.List;

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
            List<Order> orders = orderController.findOrders();

            if (orderController.exitOrder(productIdInput, quantityInput, orders)){
                break;
            }

            try {
                orderController.processOrder(productIdInput, quantityInput, orders);
            } catch (OrderInputException e) {
                System.out.println(e.getMessage());
            } catch (GlobalException e){
                System.out.println(e.getMessage());
                orderController.findOrderList(orders);
                break;
            }
        }
    }
}
