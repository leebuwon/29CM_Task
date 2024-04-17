package org.musinsa;


import org.musinsa.domain.controller.OrderController;
import org.musinsa.global.factory.SingletonFactory;

public class Main {
    public static void main(String[] args) {
        OrderController orderController = SingletonFactory.createOrderController();
        orderController.run();
    }
}
