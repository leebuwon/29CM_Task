package org.musinsa.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.musinsa.domain.order.entity.Order;
import org.musinsa.domain.product.entity.Product;
import org.musinsa.domain.order.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class OrderService {

    private static final int DELIVERY_FEE = 2500;
    private static final int FREE_DELIVERY_FEE = 50000;
    private final OrderRepository orderRepository;

    public List<Order> findOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> findExistingOrder(List<Order> orders, int productId) {
        return orderRepository.existsByOrder(orders, productId);
    }

    public void updateOrAddOrder(List<Order> orders, Order existingOrder, Product product, int quantity) {
        if (existingOrder != null){
            orderRepository.removeOrder(existingOrder);
            orders.add(existingOrder.addQuantity(quantity));
            return;
        }

        orderRepository.addOrder(new Order(product, quantity));
    }

    public Integer totalAmount(List<Order> orders) {
        return orders.stream()
                .mapToInt(order -> order.getProduct().getPrice() * order.getQuantity())
                .sum();
    }

    public Integer deliveryFee(Integer totalAmount) {
        return totalAmount < FREE_DELIVERY_FEE ? DELIVERY_FEE : 0;
    }
}
