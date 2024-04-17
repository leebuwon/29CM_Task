package org.musinsa.domain.controller;

import lombok.RequiredArgsConstructor;
import org.musinsa.domain.dto.OrderDto;
import org.musinsa.domain.entity.Order;
import org.musinsa.domain.entity.Product;
import org.musinsa.domain.exception.NotFoundProductIdException;
import org.musinsa.domain.exception.SoldOutException;
import org.musinsa.domain.service.OrderService;
import org.musinsa.domain.service.ProductService;
import org.musinsa.view.OrderListView;
import org.musinsa.view.ProductListView;

import java.util.List;

@RequiredArgsConstructor
public class OrderController {
    private final ProductService productService;
    private final OrderService orderService;
    private final ProductListView productListView;
    private final OrderListView orderListView;

    public void displayProduct() {
        displayProducts();
    }

    /**
     * 현재 상품의 List 출력
     */
    public void displayProducts() {
        Product[] products = productService.getSortedProducts();
        productListView.displayProducts(products);
    }

    /**
     * 주문 진행 과정
     */
    public boolean processOrder(String productId, String quantityId) {
        List<Order> orders = orderService.findOrders();
        if (productId.isEmpty() && quantityId.isEmpty()) {
            findOrderList(orders);
            return true;
        }

        try {
            OrderDto orderDto = new OrderDto(productId, quantityId);
            executeOrder(orderDto.getProductId(), orderDto.getQuantity(), orders);
        }  catch (SoldOutException e) {
            System.out.println(e.getMessage());
            findOrderList(orders);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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
    private void findOrderList(List<Order> orders) {
        Integer totalAmount = orderService.totalAmount(orders);
        int deliveryFee = orderService.deliveryFee(totalAmount);
        orderListView.displayOrders(orders, totalAmount, deliveryFee);
        orders.clear();
    }
}