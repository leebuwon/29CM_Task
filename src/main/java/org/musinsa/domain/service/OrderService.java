package org.musinsa.domain.service;

import org.musinsa.domain.entity.Order;
import org.musinsa.domain.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    public void printOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("주문한 내역이 없습니다.");
            return;
        }

        for (Order order : orders) {
            Product product = order.getProduct();
            int quantity = order.getQuantity();
            System.out.println(product.getName());
            System.out.println(quantity);
            System.out.println(product.getPrice() * quantity);
        }
    }
}
