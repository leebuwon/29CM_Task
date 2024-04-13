package org.musinsa.domain.controller;

import org.musinsa.domain.entity.Order;
import org.musinsa.domain.entity.Product;
import org.musinsa.domain.exception.NotFoundProductIdException;
import org.musinsa.domain.service.OrderService;
import org.musinsa.domain.service.ProductService;
import org.musinsa.view.OrderListView;
import org.musinsa.view.ProductListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProductController {
    private final ProductService productService;
    private final OrderService orderService;
    private final ProductListView productListView;
    private final OrderListView orderListView;
    private final Scanner scanner;
    private final List<Order> orders = new ArrayList<>();

    /**
     * Factory에서 생성 후 주입
     */
    public ProductController(ProductService productService, OrderService orderService,
                             ProductListView productListView, OrderListView orderListView,
                             Scanner scanner) {
        this.productService = productService;
        this.orderService = orderService;
        this.productListView = productListView;
        this.orderListView = orderListView;
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            System.out.print("입력(o[order]: 주문, q[quit]: 종료): ");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "o":
                    displayProducts();
                    processOrderWrapper();
                    break;
                case "q":
                case "quit":
                    System.out.println("고객님의 주문 감사합니다.");
                    scanner.close();
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
            System.out.print("상품번호: ");
            String productIdInput = scanner.nextLine().trim();
            System.out.print("수량: ");
            String quantityInput = scanner.nextLine().trim();

            if (processOrder(productIdInput, quantityInput)) {
                break;
            }
        }
    }

    public boolean processOrder(String productIdInput, String quantityInput) {
        if (totalOrder(productIdInput, quantityInput)) {
            return true;
        }

        int productId = Integer.parseInt(productIdInput);
        int quantity = Integer.parseInt(quantityInput);
        try {
            Product product = productService.getProductById(productId);

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
            return false;
        } catch (NotFoundProductIdException e) {
            System.out.println(e.getMessage());
            currentOrderList();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return true;
        }
    }

    private void currentOrderList() {
        if (!orders.isEmpty()) {
            Integer totalAmount = orderService.totalAmount(orders);
            orderListView.displayOrders(orders, totalAmount);
        } else {
            System.out.println("현재 주문 내역이 없습니다.");
        }
    }

    private boolean totalOrder(String productIdInput, String quantityInput) {
        if (productIdInput.isEmpty() && quantityInput.isEmpty()) {
            Integer totalAmount = orderService.totalAmount(orders);
            orderListView.displayOrders(orders, totalAmount);
            orders.clear();
            return true;
        }
        return false;
    }
}