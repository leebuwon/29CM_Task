package org.musinsa.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.musinsa.domain.exception.InvalidInputFormatException;

import java.util.Optional;

@Getter
@Builder
@AllArgsConstructor
public class OrderDto {
    private int productId;
    private int quantity;

    public OrderDto(String productIdInput, String quantityInput) {
        this.productId = parseInput(productIdInput)
                .orElseThrow(() -> new InvalidInputFormatException("InvalidInputFormatException 발생, 상품 번호는 숫자로 입력되어야 합니다."));
        this.quantity = parseInput(quantityInput)
                .orElseThrow(() -> new InvalidInputFormatException("InvalidInputFormatException 발생, 수량은 숫자로 입력되어야 합니다."));
    }

    private static Optional<Integer> parseInput(String input) {
        try {
            return Optional.of(Integer.parseInt(input));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
