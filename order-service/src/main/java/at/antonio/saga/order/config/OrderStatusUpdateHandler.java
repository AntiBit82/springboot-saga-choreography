package at.antonio.saga.order.config;

import at.antonio.saga.common.dto.OrderRequestDto;
import at.antonio.saga.common.event.OrderStatus;
import at.antonio.saga.common.event.PaymentStatus;
import at.antonio.saga.order.entity.PurchaseOrder;
import at.antonio.saga.order.repo.OrderRepository;
import at.antonio.saga.order.service.OrderStatusProducer;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class OrderStatusUpdateHandler {
  @Autowired OrderRepository orderRepository;

  @Autowired OrderStatusProducer orderStatusProducer;

  @Transactional
  public void updateOrder(int id, Consumer<PurchaseOrder> consumer) {
    orderRepository.findById(id).ifPresent(consumer.andThen(this::updateOrder));
  }

  private void updateOrder(PurchaseOrder purchaseOrder) {
    boolean paymentIsComplete = purchaseOrder.getPaymentStatus() == PaymentStatus.PAYMENT_COMPLETED;
    OrderStatus orderStatus =
        paymentIsComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
    purchaseOrder.setOrderStatus(orderStatus);

    if(!paymentIsComplete) {
        orderStatusProducer.publishOrderEvent(convertPurchaseOrderToDto(purchaseOrder), orderStatus);
    }
  }

  public OrderRequestDto convertPurchaseOrderToDto(PurchaseOrder p) {
    OrderRequestDto dto = new OrderRequestDto();
    dto.setOrderId(p.getId());
    dto.setAmount(p.getPrice());
    dto.setUserId(p.getUserId());
    dto.setProductId(p.getProductId());
    return dto;
  }
}
