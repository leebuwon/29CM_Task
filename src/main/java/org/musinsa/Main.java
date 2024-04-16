package org.musinsa;


import org.musinsa.domain.controller.ProductController;
import org.musinsa.domain.factory.SingletonFactory;

public class Main {
    public static void main(String[] args) {
        ProductController productController = SingletonFactory.createProductController();
        productController.run();
    }
}
