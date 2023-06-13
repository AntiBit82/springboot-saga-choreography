package at.antonio.saga.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private Integer orderId;
    private Integer userId;
    private Integer productId;
    private Integer amount;
}
