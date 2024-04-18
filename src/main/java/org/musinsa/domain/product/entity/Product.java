package org.musinsa.domain.product.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.musinsa.domain.order.exception.SoldOutException;

@Getter
@ToString
@AllArgsConstructor
public class Product {
    private final int id;
    private final String name;
    private final int price;
    private int stock;

    /**
     * 재고 감소
     * 재고가 주문 수량보다 적다면 exception
     */
    public void reduceStock(int quantity) {
        if (this.stock >= quantity) {
            this.stock -= quantity;
            return;
        }

        throw new SoldOutException("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
    }
}

