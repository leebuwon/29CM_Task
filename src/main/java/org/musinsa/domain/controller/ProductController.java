package org.musinsa.domain.controller;

import org.musinsa.domain.entity.Order;
import org.musinsa.domain.entity.Product;
import org.musinsa.domain.exception.NotFoundProductIdException;
import org.musinsa.domain.service.OrderService;
import org.musinsa.domain.service.ProductService;
import org.musinsa.domain.util.Console;
import org.musinsa.view.OrderListView;
import org.musinsa.view.ProductListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductController {
    private final ProductService productService;
    private final OrderService orderService;
    private final ProductListView productListView;
    private final OrderListView orderListView;
    private final Console console;
    private final List<Order> orders = new ArrayList<>();

    /**
     * Factory에서 생성 후 주입
     */
    public ProductController(ProductService productService, OrderService orderService,
                             ProductListView productListView, OrderListView orderListView,
                             Console console) {
        this.productService = productService;
        this.orderService = orderService;
        this.productListView = productListView;
        this.orderListView = orderListView;
        this.console = console;
    }

    public void run() {
        while (true) {
            String input = console.getInput("입력(o[order]: 주문, q[quit]: 종료): ");

            switch (input) {
                case "o":
                    displayProducts();
                    processOrderWrapper();
                    break;
                case "q":
                case "quit":
                    System.out.println("고객님의 주문 감사합니다.");
                    console.close();
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    break;
            }
        }
    }

    public void displayProducts() {
        Product[] products = productService.getSortedProducts();
        productListView.displayProducts(products);
    }

    private void processOrderWrapper() {
        while (true) {
            String productIdInput = console.getInput("상품번호: ");
            if (productIdInput.isEmpty()) {
                currentOrderList();
                orders.clear(); // 주문 목록을 초기화 해주는 로직
                break;
            }
            String quantityInput = console.getInput("수량: ");
            int productId = Integer.parseInt(productIdInput);
            int quantity = Integer.parseInt(quantityInput);

            processOrder(productId, quantity);
        }
    }

    public void processOrder(int productId, int quantity) {
        try {
            handleOrderProcess(productId, quantity);
        } catch (NotFoundProductIdException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            orders.clear();
        }
    }

    private void handleOrderProcess(int productId, int quantity) throws NotFoundProductIdException{
        Product product = productService.findProductId(productId);

        Optional<Order> existingOrder = orders.stream()
                .filter(order -> order.getProduct().getId() == productId)
                .findFirst();

        existingOrder.ifPresentOrElse(
                e -> {
                    orders.remove(e);
                    orders.add(e.addQuantity(quantity));
                },
                () -> orders.add(new Order(product, quantity))
        );

        productService.reduceStock(productId, quantity);
    }

    private void currentOrderList() {
        if (!orders.isEmpty()) {
            Integer totalAmount = orderService.totalAmount(orders);
            orderListView.displayOrders(orders, totalAmount);
        } else {
            System.out.println("현재 주문 내역이 없습니다.");
        }
    }
}