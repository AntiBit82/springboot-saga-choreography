package at.antonio.saga.order.entity;

import at.antonio.saga.common.dto.OrderRequestDto;
import at.antonio.saga.common.event.OrderStatus;
import at.antonio.saga.common.event.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrder {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer price;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    public static PurchaseOrder fromRequest(OrderRequestDto dto) {
        PurchaseOrder po = new PurchaseOrder();
        po.setUserId(dto.getUserId());
        po.setProductId(dto.getProductId());
        po.setPrice(dto.getAmount());
        po.setOrderStatus(OrderStatus.ORDER_CREATED);
        return po;
    }
}
