package org.musinsa.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Order {
    private final Product product;
    private final int quantity;

    /**
     * 현재 주문된 정보에서 추가로 주문된 수량 더하기
     */
    public Order addQuantity(int additionalQuantity) {
        return new Order(this.product, this.quantity + additionalQuantity);
    }
}
