package org.musinsa.domain.factory;

import org.musinsa.domain.controller.ProductController;
import org.musinsa.domain.entity.Order;
import org.musinsa.domain.service.OrderService;
import org.musinsa.domain.service.ProductService;
import org.musinsa.view.OrderListView;
import org.musinsa.view.ProductListView;

import java.util.Scanner;

public class Factory {
    public static ProductController createProductController() {
        ProductService productService = new ProductService();
        OrderService orderService = new OrderService();
        ProductListView productListView = new ProductListView();
        OrderListView orderListView = new OrderListView();
        Scanner scanner = new Scanner(System.in);

        return new ProductController(productService, orderService, productListView, orderListView, scanner);
    }
}
