package org.musinsa.domain.service;

import org.musinsa.domain.entity.Order;

import java.util.List;

public class OrderService {

    private static final int DELIVERY_FEE = 2500;
    private static final int FREE_DELIVERY_FEE = 50000;

    public Integer totalAmount(List<Order> orders) {
        if (orders.isEmpty()) {
            return null;
        }

        return orders.stream()
                .mapToInt(order -> order.getProduct().getPrice() * order.getQuantity())
                .sum();
    }

    public int deliveryFee(Integer totalAmount) {
        return totalAmount < FREE_DELIVERY_FEE ? DELIVERY_FEE : 0;
    }
}
