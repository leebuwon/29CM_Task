package org.musinsa.domain.entity;

public class Order {
    private final Product product;
    private final int quantity;

    public Order(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * 현재 주문된 정보에서 추가로 주문된 수량 더하기
     */
    public Order addQuantity(int additionalQuantity) {
        return new Order(this.product, this.quantity + additionalQuantity);
    }
}
