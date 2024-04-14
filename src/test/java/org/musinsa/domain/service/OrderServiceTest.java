package org.musinsa.domain.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.musinsa.domain.entity.Product;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("5만원 미만 일 경우 배송비가 2500원이 붙는다.")
    void addsDeliveryFeeIfOrderUnder50000() {
        Product product = Product.ITEM_768848;
        assertThat(orderService.deliveryFee(product.getPrice())).isEqualTo(2500);
    }
}