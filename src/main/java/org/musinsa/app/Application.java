package org.musinsa.app;

import org.musinsa.domain.controller.OrderController;
import org.musinsa.domain.dto.response.FindOrderListDto;
import org.musinsa.domain.entity.Order;
import org.musinsa.domain.entity.Product;
import org.musinsa.global.exception.GlobalException;
import org.musinsa.global.exception.OrderInputException;
import org.musinsa.global.factory.SingletonFactory;
import org.musinsa.view.InputView;
import org.musinsa.view.OrderListView;
import org.musinsa.view.ProductListView;

import java.util.List;

public class Application {
    InputView inputView = SingletonFactory.getInputView();
    ProductListView productListView = SingletonFactory.getProductListView();
    OrderListView orderListView = SingletonFactory.getOrderListView();
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

            if (orderController.exitOrder(productIdInput, quantityInput)){
                findOrderList(orders);
                break;
            }

            try {
                orderController.processOrder(productIdInput, quantityInput, orders);
            } catch (OrderInputException e) {
                System.out.println(e.getMessage());
            } catch (GlobalException e){
                System.out.println(e.getMessage());
                findOrderList(orders);
                break;
            }
        }
    }

    private void findOrderList(List<Order> orders) {
        FindOrderListDto dto = orderController.getTotalOrderAmount(orders);
        orderListView.displayOrders(dto.getOrders(), dto.getTotalAmount(), dto.getDeliveryFee());
        orders.clear();
    }
}
