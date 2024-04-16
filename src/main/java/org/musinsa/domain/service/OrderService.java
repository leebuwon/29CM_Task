package org.musinsa.domain.service;

import org.musinsa.domain.entity.Order;
import org.musinsa.domain.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderService {

    private static final int DELIVERY_FEE = 2500;
    private static final int FREE_DELIVERY_FEE = 50000;
    private final List<Order> orders = new ArrayList<>();

    public List<Order> findOrders() {
        return orders;
    }

    public Optional<Order> findExistingOrder(List<Order> orders, int productId) {
        return orders.stream()
                .filter(order -> order.getProduct().getId() == productId)
                .findFirst();
    }

    public void updateOrAddOrder(List<Order> orders, Order existingOrder, Product product, int quantity) {
        if (existingOrder != null){
            orders.remove(existingOrder);
            orders.add(existingOrder.addQuantity(quantity));
            return;
        }

        orders.add(new Order(product, quantity));
    }

    public Integer totalAmount(List<Order> orders) {
        return orders.stream()
                .mapToInt(order -> order.getProduct().getPrice() * order.getQuantity())
                .sum();
    }

    public int deliveryFee(Integer totalAmount) {
        return totalAmount < FREE_DELIVERY_FEE ? DELIVERY_FEE : 0;
    }
}
