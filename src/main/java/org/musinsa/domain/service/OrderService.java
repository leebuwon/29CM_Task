package org.musinsa.domain.service;

import org.musinsa.domain.entity.Order;
import org.musinsa.domain.entity.Product;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private static final int deliveryFee = 2500;
    private static final DecimalFormat formatter = new DecimalFormat("#,##0원");

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
            System.out.println(product.getName() + " - " + order.getQuantity() + "개");
        }

        System.out.println("----------------------------------");
        System.out.println("주문 금액: " + formatter.format(totalAmount));
        if (totalAmount < 50000) {
            System.out.println("배송비: " + formatter.format(deliveryFee));
            totalAmount += deliveryFee;
        }
        System.out.println("----------------------------------");
        System.out.println("지불금액: " + formatter.format(totalAmount));
        System.out.println("----------------------------------");
        System.out.print("\n");
    }
}
