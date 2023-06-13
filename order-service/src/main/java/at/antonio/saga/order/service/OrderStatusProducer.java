package at.antonio.saga.order.service;

import at.antonio.saga.common.dto.OrderRequestDto;
import at.antonio.saga.common.event.OrderEvent;
import at.antonio.saga.common.event.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class OrderStatusProducer {
    @Autowired
    private Sinks.Many<OrderEvent> orderSink;

    public void publishOrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
        OrderEvent e = new OrderEvent(orderRequestDto, orderStatus);
        orderSink.tryEmitNext(e);
    }
}
