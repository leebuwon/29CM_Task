package org.musinsa.domain.controller;

import org.musinsa.domain.entity.Order;
import org.musinsa.domain.entity.Product;
import org.musinsa.domain.service.OrderService;
import org.musinsa.domain.service.ProductService;
import org.musinsa.view.ProductListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductController {
    private final ProductService productService;
    private final OrderService orderService;
    private final ProductListView productListView;
    private final Scanner scanner;
    private final List<Order> orders = new ArrayList<>();

    /**
     * Factory에서 생성 후 주입
     */
    public ProductController(ProductService productService, OrderService orderService, ProductListView productListView, Scanner scanner) {
        this.productService = productService;
        this.orderService = orderService;
        this.productListView = productListView;
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            System.out.print("입력(o[order]: 주문, q[quit]: 종료): ");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "o":
                    displayProducts();
                    processOrder();
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

    public void processOrder() {
        while (true) {
            System.out.print("상품번호: ");
            String productIdInput = scanner.nextLine();
            System.out.print("수량: ");
            String quantityInput = scanner.nextLine();

            if (displayOrders(productIdInput, quantityInput)) {
                orders.clear();
                break;
            }

            try {
                int productId = Integer.parseInt(productIdInput.trim());
                int quantity = Integer.parseInt(quantityInput.trim());
                productService.reduceStock(productId, quantity);
                Product product = productService.getProductById(productId);

                Order existingOrder = null;
                for (Order order : orders) {
                    if (order.getProduct().getId() == productId) {
                        existingOrder = order;
                        break;
                    }
                }

                if (existingOrder != null) {
                    orders.remove(existingOrder);
                    orders.add(existingOrder.addQuantity(quantity));
                } else {
                    orders.add(new Order(product, quantity));
                }
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            }
        }
    }

    private boolean displayOrders(String productIdInput, String quantityInput) {
        if (productIdInput.trim().isEmpty() && quantityInput.trim().isEmpty()) {
            orderService.displayOrders(orders);
            return true;
        }
        return false;
    }
}