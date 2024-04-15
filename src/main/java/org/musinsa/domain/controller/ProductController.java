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
                    processOrderInput();
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

    /**
     * 현재 상품의 List 출력
     */
    public void displayProducts() {
        Product[] products = productService.getSortedProducts();
        productListView.displayProducts(products);
    }

    /**
     * 상품번호와 수량을 입력
     */
    private void processOrderInput() {
        while (true) {
            String productIdInput = console.getInput("상품번호: ");
            String quantityInput = console.getInput("수량: ");
            if (exitOrder(productIdInput, quantityInput)) break;

            int productId = Integer.parseInt(productIdInput);
            int quantity = Integer.parseInt(quantityInput);

            if (processOrder(productId, quantity)) break;
        }
    }

    /**
     * 상품 주문 나가기
     */
    private boolean exitOrder(String productIdInput, String quantityInput) {
        if (productIdInput.isEmpty() && quantityInput.isEmpty()) {
            findOrderList();
            orders.clear();
            return true;
        }
        return false;
    }

    /**
     * executeOrder를 통하여 실제 주문시작
     * catch를 통해 exception 처리
     */
    public boolean processOrder(int productId, int quantity) {
        try {
            executeOrder(productId, quantity);
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

    /**
     * 주문의 비즈니스 로직
     * 잔고 감소, 여러차례 구매한 목록에 대한 수량 증가
     */
    public synchronized void executeOrder(int productId, int quantity) throws NotFoundProductIdException{
        Product product = productService.findProductId(productId);
        Order existingOrder = orderService.findExistingOrder(orders, productId)
                .orElse(null);

        orderService.updateOrAddOrder(orders, existingOrder, product, quantity);
        productService.reduceStock(productId, quantity);
    }

    /**
     * 최종 주문 구매에 대한 List 출력
     */
    private void findOrderList() {
        if (!orders.isEmpty()) {
            Integer totalAmount = orderService.totalAmount(orders);
            int deliveryFee = orderService.deliveryFee(totalAmount);
            orderListView.displayOrders(orders, totalAmount, deliveryFee);
            return;
        }

        System.out.println("현재 주문 내역이 없습니다.");
    }
}