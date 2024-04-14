package org.musinsa.domain.service;

import org.musinsa.domain.entity.Order;

import java.util.List;

public class OrderService {

    public Integer totalAmount(List<Order> orders) {
        if (orders.isEmpty()) {
            return null;
        }

        return orders.stream()
                .mapToInt(order -> order.getProduct().getPrice() * order.getQuantity())
                .sum();
    }
}
