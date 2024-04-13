package org.musinsa.domain.service;

import org.musinsa.domain.entity.Order;
import org.musinsa.domain.entity.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private static final int deliveryFee = 2500;

    public void displayOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("주문한 내역이 없습니다.");
            return;
        }

        int totalAmount = 0;
        Map<Integer, Order> finalOrders = new HashMap<>();
        for (Order order : orders) {
            Product product = order.getProduct();
            int productId = product.getId();
            int amount = product.getPrice() * order.getQuantity();
            totalAmount += amount;

            if (finalOrders.containsKey(productId)) {
                Order existingOrder = finalOrders.get(productId);
                finalOrders.put(productId, existingOrder.addQuantity(order.getQuantity()));
            } else {
                finalOrders.put(productId, order);
            }
        }

        System.out.println("----------------------------------");
        for (Order order : finalOrders.values()) {
            Product product = order.getProduct();
            System.out.println(product.getName() + " - " + order.getQuantity());
        }

        System.out.println("----------------------------------");
        if (totalAmount < 50000) {
            System.out.println("배송비: " + deliveryFee);
            totalAmount += deliveryFee;
        }
        System.out.println("지불금액: " + totalAmount);
        System.out.println("----------------------------------");
    }
}
