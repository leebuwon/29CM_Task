package org.musinsa.app.view;

import org.musinsa.domain.order.entity.Order;
import org.musinsa.domain.product.entity.Product;

import java.text.DecimalFormat;
import java.util.List;

public class OrderListView {

    private static final String LINE = "-------------------------------------";
    private static final String HAS_NO_ORDER = "주문한 내역이 없습니다.";
    private static final String ORDER_HISTORY = "주문 내역: ";
    private static final String ORDER_PAY = "주문금액: ";
    private static final String DELIVERY_FEE = "배송비: ";
    private static final String TOTAL_PAYMENT = "지불금액: ";
    private static final DecimalFormat formatter = new DecimalFormat("#,##0원");

    public void displayOrders(List<Order> orders, Integer totalAmount, Integer deliveryFee) {
        if (totalAmount == null || orders.isEmpty()) {
            System.out.println(HAS_NO_ORDER);
            return;
        }

        System.out.println(ORDER_HISTORY);
        System.out.println(LINE);
        for (Order order : orders) {
            Product product = order.getProduct();
            System.out.println(product.getName() + " - " + order.getQuantity() + "개");
        }

        System.out.println(LINE);
        System.out.println(ORDER_PAY + formatter.format(totalAmount));
        if (deliveryFee > 0) {
            System.out.println(DELIVERY_FEE + formatter.format(deliveryFee));
        }

        System.out.println(LINE);
        System.out.println(TOTAL_PAYMENT + formatter.format(totalAmount + deliveryFee));
        System.out.println(LINE);
        System.out.print("\n");
    }
}
