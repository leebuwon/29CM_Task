package org.musinsa.domain.repository;

import org.musinsa.domain.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> findAll();
    Optional<Order> existsByOrder(List<Order> orders, int productId);
    void addOrder(Order order);
    void removeOrder(Order order);
}
