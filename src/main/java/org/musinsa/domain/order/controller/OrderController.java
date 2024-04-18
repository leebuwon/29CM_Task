package org.musinsa.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.musinsa.domain.order.dto.request.OrderProductIdDto;
import org.musinsa.domain.order.dto.response.FindOrderListDto;
import org.musinsa.domain.order.dto.request.OrderQuantityDto;
import org.musinsa.domain.order.entity.Order;
import org.musinsa.domain.product.entity.Product;
import org.musinsa.domain.order.service.OrderService;

import java.util.List;

@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /**
     * 주문 조회
     */
    public List<Order> findOrders() {
        return orderService.findOrders();
    }

    /**
     * 주문 종료
     */
    public boolean exitOrder(String productId, String quantityId) {
        return productId.isEmpty() && quantityId.isEmpty();
    }

    /**
     * 주문 과정에서 ProductId 찾기 (PathVariable 종류)
     */
    public Product findProduct(String productId) {
        OrderProductIdDto dto = new OrderProductIdDto(productId);
        return orderService.findByProduct(dto.getProductId());
    }

    /**
     * 주문 진행(잔고 감소, 여러차례 구매한 목록에 대한 수량 증가)
     */
    public synchronized void processOrder(Product product, String quantity, List<Order> orders) {
        OrderQuantityDto dto = new OrderQuantityDto(quantity);
        Order existingOrder = orderService.findExistingOrder(orders, product.getId())
                .orElse(null);

        orderService.reduceStock(product, dto.getQuantity());
        orderService.updateOrAddOrder(orders, existingOrder, product, dto.getQuantity());
    }

    /**
     * 최종 주문 금액
     */
    public FindOrderListDto getTotalOrderAmount(List<Order> orders){
        Integer totalAmount = orderService.calculateTotalAmount(orders);
        Integer deliveryFee = orderService.calculateDeliveryFee(totalAmount);
        return FindOrderListDto.from(orders, totalAmount, deliveryFee);
    }
}