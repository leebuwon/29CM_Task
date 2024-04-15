package org.musinsa.domain.factory;

import org.musinsa.domain.controller.ProductController;
import org.musinsa.domain.service.OrderService;
import org.musinsa.domain.service.ProductService;
import org.musinsa.domain.util.Console;
import org.musinsa.view.InputView;
import org.musinsa.view.OrderListView;
import org.musinsa.view.ProductListView;

public class Factory {
    public static ProductController createProductController() {
        ProductService productService = new ProductService();
        OrderService orderService = new OrderService();
        ProductListView productListView = new ProductListView();
        OrderListView orderListView = new OrderListView();
        Console console = new Console();
        InputView inputView = new InputView(console);

        return new ProductController(productService, orderService, productListView, orderListView, inputView);
    }
}
