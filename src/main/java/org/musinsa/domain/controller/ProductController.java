package org.musinsa.domain.controller;

import org.musinsa.domain.entity.Order;
import org.musinsa.domain.entity.Product;
import org.musinsa.domain.service.OrderService;
import org.musinsa.domain.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductController {
    private final ProductService productService;
    private final OrderService orderService;
    private final List<Order> orders = new ArrayList<>();

    public ProductController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    public void displayProducts() {
        Product[] products = productService.getSortedProducts();
        System.out.println(String.format("%-8s %-28s %-7s %s", "상품번호", "상품명", "판매가격", "재고수"));
        for (Product product : products) {
            System.out.println(String.format("%-10d %-30s %-12d %d", product.getId(), product.getName(), product.getPrice(), product.getStock()));
        }
        System.out.print("\n");
    }

    public void processOrder(Scanner scanner) {
        while (true) {
            System.out.print("상품번호: ");
            String productIdInput = scanner.nextLine();
            System.out.print("수량: ");
            String quantityInput = scanner.nextLine();

            if (productIdInput.trim().isEmpty() && quantityInput.trim().isEmpty()) {
                orderService.printOrders(orders);
                break;
            }

            try {
                int productId = Integer.parseInt(productIdInput.trim());
                int quantity = Integer.parseInt(quantityInput.trim());
                productService.reduceStock(productId, quantity);
                Product product = productService.getProductById(productId);
                orders.add(new Order(product, quantity));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }
}