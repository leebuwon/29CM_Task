package org.musinsa.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private int productId;
    private int quantity;

    public OrderDto(String productIdInput, String quantityInput) throws NumberFormatException {
        try {
            this.productId = Integer.parseInt(productIdInput);
            this.quantity = Integer.parseInt(quantityInput);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("유효하지 않은 입력: 상품번호와 수량이 숫자인지 확인하세요.");
        }
    }
}
