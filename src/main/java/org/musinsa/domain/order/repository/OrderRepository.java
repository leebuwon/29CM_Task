package org.musinsa.domain.order.repository;

import org.musinsa.domain.order.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> findAll();
    Optional<Order> existsByOrder(List<Order> orders, int productId);
    void addOrder(Order order);
    void removeOrder(Order order);
}
