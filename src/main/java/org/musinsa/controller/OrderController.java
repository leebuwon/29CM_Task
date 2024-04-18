package org.musinsa.controller;

import lombok.RequiredArgsConstructor;
import org.musinsa.domain.order.dto.response.FindOrderListDto;
import org.musinsa.domain.order.dto.request.OrderDto;
import org.musinsa.domain.order.entity.Order;
import org.musinsa.domain.product.entity.Product;
import org.musinsa.domain.order.exception.NotFoundProductIdException;
import org.musinsa.domain.order.service.OrderService;
import org.musinsa.domain.product.service.ProductService;

import java.util.List;

@RequiredArgsConstructor
public class OrderController {
    private final ProductService productService;
    private final OrderService orderService;

    /**
     * 현재 상품의 List 출력
     */
    public Product[] displayProducts() {
        return productService.getSortedProducts();
    }

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
        if (productId.isEmpty() && quantityId.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 주문 진행 과정
     */
    public void processOrder(String productId, String quantityId, List<Order> orders) {
        OrderDto orderDto = new OrderDto(productId, quantityId);
        executeOrder(orderDto.getProductId(), orderDto.getQuantity(), orders);
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
     * 최종 주문 금액
     */
    public FindOrderListDto getTotalOrderAmount(List<Order> orders){
        Integer totalAmount = orderService.totalAmount(orders);
        Integer deliveryFee = orderService.deliveryFee(totalAmount);
        return FindOrderListDto.from(orders, totalAmount, deliveryFee);
    }
}