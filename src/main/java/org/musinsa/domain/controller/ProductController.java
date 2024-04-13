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
    private final List<Order> orders = new ArrayList<>();

    public ProductController(ProductService productService, OrderService orderService, ProductListView productListView) {
        this.productService = productService;
        this.orderService = orderService;
        this.productListView = productListView;
    }

    public void displayProducts() {
        Product[] products = productService.getSortedProducts();
        productListView.displayProducts(products);
    }

    public void processOrder(Scanner scanner) {
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
                System.out.println(e.getMessage());
                break;
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