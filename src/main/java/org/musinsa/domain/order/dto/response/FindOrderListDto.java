package org.musinsa.domain.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.musinsa.domain.order.entity.Order;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class FindOrderListDto {

    private Integer totalAmount;
    private Integer deliveryFee;
    private List<Order> orders;

    public static FindOrderListDto from(List<Order> orders, Integer totalAmount, Integer deliveryFee){
        return FindOrderListDto.builder()
                .orders(orders)
                .totalAmount(totalAmount)
                .deliveryFee(deliveryFee)
                .build();
    }
}
