package at.antonio.saga.common.dto;

import at.antonio.saga.common.event.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Integer orderId;
    private Integer userId;
    private Integer productId;
    private Integer amount;
    private OrderStatus orderStatus;
}
