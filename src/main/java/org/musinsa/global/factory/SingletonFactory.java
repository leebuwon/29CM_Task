package org.musinsa.global.factory;

import org.musinsa.domain.order.controller.OrderController;
import org.musinsa.domain.order.repository.OrderRepository;
import org.musinsa.domain.order.repository.impl.OrderRepositoryImpl;
import org.musinsa.domain.order.service.OrderService;
import org.musinsa.domain.product.controller.ProductController;
import org.musinsa.domain.product.repository.ProductRepository;
import org.musinsa.domain.product.repository.impl.ProductRepositoryImpl;
import org.musinsa.domain.product.service.ProductService;
import org.musinsa.global.util.Console;
import org.musinsa.app.view.InputView;
import org.musinsa.app.view.OrderListView;
import org.musinsa.app.view.ProductListView;

public class SingletonFactory {

    private SingletonFactory() {}

    private static final class ProductServiceHolder {
        private static final ProductService productService = new ProductService(getProductRepository());
    }

    private static final class ProductRepositoryHolder {
        private static final ProductRepository productRepository = new ProductRepositoryImpl();
    }

    private static final class OrderServiceHolder {
        private static final OrderService orderService = new OrderService(getProductService(), getOrderRepository());
    }

    private static final class OrderRepositoryHolder {
        private static final OrderRepository orderRepository = new OrderRepositoryImpl();
    }

    private static final class ProductListViewHolder {
        private static final ProductListView productListView = new ProductListView();
    }

    private static final class OrderListViewHolder {
        private static final OrderListView orderListView = new OrderListView();
    }

    private static final class ConsoleHolder {
        private static final Console console = new Console();
    }

    private static final class InputViewHolder {
        private static final InputView inputView = new InputView(getConsole());
    }

    public static ProductService getProductService() {
        return ProductServiceHolder.productService;
    }

    public static ProductRepository getProductRepository() {
        return ProductRepositoryHolder.productRepository;
    }

    public static OrderService getOrderService() {
        return OrderServiceHolder.orderService;
    }

    public static OrderRepository getOrderRepository() {
        return OrderRepositoryHolder.orderRepository;
    }

    public static ProductListView getProductListView() {
        return ProductListViewHolder.productListView;
    }

    public static OrderListView getOrderListView() {
        return OrderListViewHolder.orderListView;
    }

    public static Console getConsole() {
        return ConsoleHolder.console;
    }

    public static InputView getInputView() {
        return InputViewHolder.inputView;
    }


    public static OrderController createOrderController() {
        return new OrderController(getOrderService());
    }

    public static ProductController createProductController() {
        return new ProductController(getProductService());
    }

}
