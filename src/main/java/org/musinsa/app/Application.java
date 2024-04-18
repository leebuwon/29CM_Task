package org.musinsa.app;

import org.musinsa.app.view.InputView;
import org.musinsa.app.view.OrderListView;
import org.musinsa.app.view.ProductListView;
import org.musinsa.domain.order.controller.OrderController;
import org.musinsa.domain.order.dto.response.FindOrderListDto;
import org.musinsa.domain.order.entity.Order;
import org.musinsa.domain.product.controller.ProductController;
import org.musinsa.domain.product.entity.Product;
import org.musinsa.global.exception.GlobalException;
import org.musinsa.global.exception.OrderInputException;
import org.musinsa.global.factory.SingletonFactory;

import java.util.List;

public class Application {
    InputView inputView = SingletonFactory.getInputView();
    ProductListView productListView = SingletonFactory.getProductListView();
    OrderListView orderListView = SingletonFactory.getOrderListView();
    OrderController orderController = SingletonFactory.createOrderController();
    ProductController productController = SingletonFactory.createProductController();

    public void run() {
        while (true) {
            String input = inputView.getInput();

            switch (input) {
                case "o" -> {
                    List<Product> products = productController.displayProducts();
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
                Product product = orderController.findProduct(productIdInput);
                orderController.processOrder(product, quantityInput, orders);
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
