package org.musinsa.view;

import org.musinsa.domain.entity.Order;
import org.musinsa.domain.entity.Product;

import java.text.DecimalFormat;
import java.util.List;

public class OrderListView {

    private static final String line = "-------------------------------------";
    private static final int deliveryFee = 2500;
    private static final DecimalFormat formatter = new DecimalFormat("#,##0원");

    public void displayOrders(List<Order> orders, Integer totalAmount) {
        if (totalAmount == null || orders.isEmpty()) {
            System.out.println("주문한 내역이 없습니다.");
            return;
        }

        System.out.println("주문 내역: ");
        System.out.println(line);
        for (Order order : orders) {
            Product product = order.getProduct();
            System.out.println(product.getName() + " - " + order.getQuantity() + "개");
        }

        System.out.println(line);
        System.out.println("주문 금액: " + formatter.format(totalAmount));
        if (totalAmount < 50000) {
            System.out.println("배송비: " + formatter.format(deliveryFee));
            totalAmount += deliveryFee;
        }
        System.out.println(line);
        System.out.println("지불금액: " + formatter.format(totalAmount));
        System.out.println(line);
        System.out.print("\n");
    }
}
