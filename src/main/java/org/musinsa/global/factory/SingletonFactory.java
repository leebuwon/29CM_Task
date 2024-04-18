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
        private static final ProductService PRODUCT_SERVICE = new ProductService(getProductRepository());
    }

    private static final class ProductRepositoryHolder {
        private static final ProductRepository PRODUCT_REPOSITORY = new ProductRepositoryImpl();
    }

    private static final class OrderServiceHolder {
        private static final OrderService ORDER_SERVICE = new OrderService(getProductService(), getOrderRepository());
    }

    private static final class OrderRepositoryHolder {
        private static final OrderRepository ORDER_REPOSITORY = new OrderRepositoryImpl();
    }

    private static final class ProductListViewHolder {
        private static final ProductListView PRODUCT_LIST_VIEW = new ProductListView();
    }

    private static final class OrderListViewHolder {
        private static final OrderListView ORDER_LIST_VIEW = new OrderListView();
    }

    private static final class ConsoleHolder {
        private static final Console CONSOLE = new Console();
    }

    private static final class InputViewHolder {
        private static final InputView INPUT_VIEW = new InputView(getConsole());
    }

    public static ProductService getProductService() {
        return ProductServiceHolder.PRODUCT_SERVICE;
    }

    public static ProductRepository getProductRepository() {
        return ProductRepositoryHolder.PRODUCT_REPOSITORY;
    }

    public static OrderService getOrderService() {
        return OrderServiceHolder.ORDER_SERVICE;
    }

    public static OrderRepository getOrderRepository() {
        return OrderRepositoryHolder.ORDER_REPOSITORY;
    }

    public static ProductListView getProductListView() {
        return ProductListViewHolder.PRODUCT_LIST_VIEW;
    }

    public static OrderListView getOrderListView() {
        return OrderListViewHolder.ORDER_LIST_VIEW;
    }

    public static Console getConsole() {
        return ConsoleHolder.CONSOLE;
    }

    public static InputView getInputView() {
        return InputViewHolder.INPUT_VIEW;
    }


    public static OrderController createOrderController() {
        return new OrderController(getOrderService());
    }

    public static ProductController createProductController() {
        return new ProductController(getProductService());
    }

}
