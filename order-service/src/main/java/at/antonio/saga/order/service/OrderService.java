package at.antonio.saga.order.service;

import at.antonio.saga.common.dto.OrderRequestDto;
import at.antonio.saga.common.event.OrderStatus;
import at.antonio.saga.order.entity.PurchaseOrder;
import at.antonio.saga.order.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
  @Autowired private OrderRepository orderRepository;

  @Autowired
  private OrderStatusProducer orderStatusProducer;

  @Transactional
  public PurchaseOrder createOrder(OrderRequestDto orderRequestDto) {
     PurchaseOrder order = orderRepository.save(PurchaseOrder.fromRequest(orderRequestDto));
     orderRequestDto.setOrderId(order.getId());
     orderStatusProducer.publishOrderEvent(orderRequestDto, OrderStatus.ORDER_CREATED);
     return order;
  }

  public List<PurchaseOrder> getAllOrders() {
      return orderRepository.findAll();
  }
}
