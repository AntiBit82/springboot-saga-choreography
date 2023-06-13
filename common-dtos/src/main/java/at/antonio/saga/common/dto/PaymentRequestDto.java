package at.antonio.saga.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {
    private Integer orderId;
    private Integer userId;
    private Integer amount;
}
