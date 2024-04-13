package org.musinsa.domain.service;

import org.musinsa.domain.entity.Order;
import org.musinsa.domain.entity.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    public Integer totalAmount(List<Order> orders) {
        if (orders.isEmpty()) {
            return null;
        }

        int totalAmount = 0;
        Map<Integer, Order> finalOrders = new HashMap<>();
        for (Order order : orders) {
            Product product = order.getProduct();
            int productId = product.getId();
            int amount = product.getPrice() * order.getQuantity();
            totalAmount += amount;

            finalOrders.merge(productId, order, (existingOrder, newOrder)
                    -> existingOrder.addQuantity(newOrder.getQuantity()));
        }

        return totalAmount;
    }
}
