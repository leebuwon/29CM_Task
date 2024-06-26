package org.musinsa.domain.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.musinsa.domain.order.exception.InvalidInputFormatException;

import java.util.Optional;

@Getter
@Builder
@AllArgsConstructor
public class OrderProductIdDto {

    private int productId;

    public OrderProductIdDto(String productIdInput) {
        this.productId = parseInput(productIdInput)
                .orElseThrow(() -> new InvalidInputFormatException("InvalidInputFormatException 발생, 상품번호는 숫자로 입력되어야 합니다."));
    }

    private static Optional<Integer> parseInput(String input) {
        try {
            return Optional.of(Integer.parseInt(input));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
