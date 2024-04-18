package org.musinsa.domain.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.musinsa.domain.order.exception.InvalidInputFormatException;

import java.util.Optional;

@Getter
@Builder
@AllArgsConstructor
public class OrderQuantityDto {
    private int quantity;

    public OrderQuantityDto(String quantityInput) {
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
