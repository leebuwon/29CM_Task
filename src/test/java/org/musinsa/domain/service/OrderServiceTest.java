package org.musinsa.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.musinsa.domain.order.entity.Order;
import org.musinsa.domain.order.service.OrderService;
import org.musinsa.domain.product.entity.Product;
import org.musinsa.domain.order.repository.impl.OrderRepositoryImpl;
import org.musinsa.domain.product.service.ProductService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;
    List<Order> orders;

    @BeforeEach
    void setUp() {
        OrderRepositoryImpl orderRepositoryImpl = new OrderRepositoryImpl();
        ProductService productService = new ProductService();
        orderService = new OrderService(productService, orderRepositoryImpl);
        orders = orderService.findOrders();
    }

    @Test
    @DisplayName("5만원 미만 일 경우 배송비가 2500원이 붙는다.")
    void addsDeliveryFeeIfOrderUnder50000_success() {
        Product product = orderService.findByProduct(768848);
        assertThat(orderService.deliveryFee(product.getPrice())).isEqualTo(2500);
    }

    @Test
    @DisplayName("같은 품목을 2번 주문하면 quantity를 더해서 보여준다.")
    void addDuplicateOrderQuantity_success() {
        Product product = orderService.findByProduct(502480);
        Order existingOrder;

        existingOrder = orderService.findExistingOrder(orders, product.getId())
                .orElse(null);
        orderService.updateOrAddOrder(orders, existingOrder, product, 10);

        existingOrder = orderService.findExistingOrder(orders, product.getId())
                .orElse(null);
        orderService.updateOrAddOrder(orders, existingOrder, product, 10);

        assertThat(orders.size()).isEqualTo(1);
        assertThat(orders.get(0).getQuantity()).isEqualTo(20);
    }

    @Test
    @DisplayName("버드와이저 상품을 10개 구매하면 35만원이 나온다.")
    void calculateTotalAmount_success() {
        Product product = orderService.findByProduct(779989);

        Order existingOrder;

        existingOrder = orderService.findExistingOrder(orders, product.getId())
                .orElse(null);
        orderService.updateOrAddOrder(orders, existingOrder, product, 10);

        Integer totalAmount = orderService.totalAmount(orders);
        assertThat(totalAmount).isEqualTo(350000);
    }
}