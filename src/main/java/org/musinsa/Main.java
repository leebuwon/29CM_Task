package org.musinsa;


import org.musinsa.domain.controller.ProductController;
import org.musinsa.domain.factory.Factory;

public class Main {
    public static void main(String[] args) {
        ProductController productController = Factory.createProductController();
        productController.run();
    }
}
