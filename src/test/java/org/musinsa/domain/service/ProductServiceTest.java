package org.musinsa.domain.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.musinsa.domain.order.exception.NotFoundProductIdException;
import org.musinsa.domain.product.service.ProductService;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("상품 번호를 잘못 입력하였을 경우 NotFoundProductIdException 발생")
    void notFoundProductException_success() {
        int productId = 123456;
        Assertions.assertThatThrownBy(() -> productService.findProductId(productId))
                .isInstanceOf(NotFoundProductIdException.class)
                .hasMessageContaining("NotFoundProductIdException 발생, 잘못된 상품번호입니다.");
    }

}