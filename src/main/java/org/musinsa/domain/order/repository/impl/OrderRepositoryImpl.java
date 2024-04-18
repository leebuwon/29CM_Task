package org.musinsa.domain.order.repository.impl;

import org.musinsa.domain.order.entity.Order;
import org.musinsa.domain.order.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {
    private final List<Order> orders = new ArrayList<>();

    @Override
    public List<Order> findAll() {
        return orders;
    }

    @Override
    public Optional<Order> existsByOrder(List<Order> orders, int productId) {
        return orders.stream()
                .filter(order -> order.getProduct().getId() == productId)
                .findFirst();
    }

    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public void removeOrder(Order order) {
        orders.remove(order);
    }
}
