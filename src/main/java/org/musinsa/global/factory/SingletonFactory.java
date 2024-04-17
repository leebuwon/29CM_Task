package org.musinsa.global.factory;

import org.musinsa.domain.controller.OrderController;
import org.musinsa.domain.repository.OrderRepository;
import org.musinsa.domain.repository.impl.OrderRepositoryImpl;
import org.musinsa.domain.service.OrderService;
import org.musinsa.domain.service.ProductService;
import org.musinsa.global.util.Console;
import org.musinsa.view.InputView;
import org.musinsa.view.OrderListView;
import org.musinsa.view.ProductListView;

public class SingletonFactory {

    private SingletonFactory() {}

    private static final class ProductServiceHolder {
        private static final ProductService productService = new ProductService();
    }

    private static final class OrderServiceHolder {
        private static final OrderService orderService = new OrderService(getOrderRepository());
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
        return new OrderController(getProductService(), getOrderService(), getProductListView(), getOrderListView(), getInputView());
    }
}
