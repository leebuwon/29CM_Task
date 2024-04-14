package org.musinsa.domain.controller;

import lombok.AllArgsConstructor;
import org.musinsa.domain.entity.Order;
import org.musinsa.domain.entity.Product;
import org.musinsa.domain.exception.NotFoundProductIdException;
import org.musinsa.domain.exception.SoldOutException;
import org.musinsa.domain.service.OrderService;
import org.musinsa.domain.service.ProductService;
import org.musinsa.domain.util.Console;
import org.musinsa.view.OrderListView;
import org.musinsa.view.ProductListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final OrderService orderService;
    private final ProductListView productListView;
    private final OrderListView orderListView;
    private final Console console;
    private final List<Order> orders = new ArrayList<>();

    public void run() {
        while (true) {
            String input = console.getInput("입력(o[order]: 주문, q[quit]: 종료): ");

            switch (input) {
                case "o" -> {
                    displayProducts();
                    processOrderWrapper();
                }
                case "q", "quit" -> {
                    System.out.println("고객님의 주문 감사합니다.");
                    console.close();
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
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
            String quantityInput = console.getInput("수량: ");
            if (exitOrder(productIdInput, quantityInput)) break;

            int productId = Integer.parseInt(productIdInput);
            int quantity = Integer.parseInt(quantityInput);

            if (processOrder(productId, quantity)) break;

        }
    }

    private boolean exitOrder(String productIdInput, String quantityInput) {
        if (productIdInput.isEmpty() && quantityInput.isEmpty()) {
            finalOrderList();
            orders.clear(); // 주문 목록을 초기화 해주는 로직
            return true;
        }
        return false;
    }

    public boolean processOrder(int productId, int quantity) {
        try {
            handleOrderProcess(productId, quantity);
            return false;
        } catch (NotFoundProductIdException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (SoldOutException e) {
            System.out.println(e.getMessage());
            orders.clear();
            return true;
        }
    }

    public synchronized void handleOrderProcess(int productId, int quantity) throws NotFoundProductIdException{
        Product product = productService.findProductId(productId);
        Order existingOrder = findExistingOrder(productId)
                .orElse(null);

        existingOrderOrNot(existingOrder, product, quantity);
        productService.reduceStock(productId, quantity);
    }

    private void existingOrderOrNot(Order existingOrder, Product product, int quantity) {
        if (existingOrder != null){
            orders.remove(existingOrder);
            orders.add(existingOrder.addQuantity(quantity));
            return;
        }

        orders.add(new Order(product, quantity));
    }

    private Optional<Order> findExistingOrder(int productId) {
        return orders.stream()
                .filter(order -> order.getProduct().getId() == productId)
                .findFirst();
    }

    private void finalOrderList() {
        if (!orders.isEmpty()) {
            Integer totalAmount = orderService.totalAmount(orders);
            orderListView.displayOrders(orders, totalAmount);
            return;
        }

        System.out.println("현재 주문 내역이 없습니다.");
    }
}