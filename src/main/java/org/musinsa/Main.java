package org.musinsa;


import org.musinsa.app.Application;
import org.musinsa.domain.controller.OrderController;
import org.musinsa.global.factory.SingletonFactory;

public class Main {
    public static void main(String[] args) {
        Application app = new Application();
        app.run();

    }
}
