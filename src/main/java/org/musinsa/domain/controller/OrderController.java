package org.musinsa.domain.controller;

import lombok.RequiredArgsConstructor;
import org.musinsa.domain.dto.OrderDto;
import org.musinsa.domain.entity.Order;
import org.musinsa.domain.entity.Product;
import org.musinsa.domain.exception.NotFoundProductIdException;
import org.musinsa.domain.service.OrderService;
import org.musinsa.domain.service.ProductService;
import org.musinsa.view.OrderListView;

import java.util.List;

@RequiredArgsConstructor
public class OrderController {
    private final ProductService productService;
    private final OrderService orderService;
    private final OrderListView orderListView;

    /**
     * 현재 상품의 List 출력
     */
    public Product[] displayProducts() {
        return productService.getSortedProducts();
    }

    public List<Order> findOrders() {
        return orderService.findOrders();
    }

    /**
     * 주문 진행 과정
     */
    public boolean processOrder(String productId, String quantityId, List<Order> orders) {
        if (productId.isEmpty() && quantityId.isEmpty()) {
            findOrderList(orders);
            return true;
        }

        OrderDto orderDto = new OrderDto(productId, quantityId);
        executeOrder(orderDto.getProductId(), orderDto.getQuantity(), orders);
        return false;
    }

    /**
     * 잔고 감소, 여러차례 구매한 목록에 대한 수량 증가
     */
    public synchronized void executeOrder(int productId, int quantity, List<Order> orders) throws NotFoundProductIdException{
        Product product = productService.findProductId(productId);
        Order existingOrder = orderService.findExistingOrder(orders, productId)
                .orElse(null);

        productService.reduceStock(productId, quantity);
        orderService.updateOrAddOrder(orders, existingOrder, product, quantity);
    }

    /**
     * 최종 주문 구매에 대한 List 출력
     */
    public void findOrderList(List<Order> orders) {
        Integer totalAmount = orderService.totalAmount(orders);
        int deliveryFee = orderService.deliveryFee(totalAmount);
        orderListView.displayOrders(orders, totalAmount, deliveryFee);
        orders.clear();
    }
}