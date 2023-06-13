package at.antonio.saga.payment.config;

import at.antonio.saga.common.event.OrderEvent;
import at.antonio.saga.common.event.OrderStatus;
import at.antonio.saga.common.event.PaymentEvent;
import at.antonio.saga.payment.service.PaymentService;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class OrderConsumerConfig {

    @Autowired
    private PaymentService paymentService;

    @Bean
    public Function<Flux<OrderEvent>, Flux<PaymentEvent>> paymentProcessor() {
        return orderEventFlux -> orderEventFlux.flatMap(this::processOrder);
    }

    private Mono<PaymentEvent> processOrder(OrderEvent orderEvent) {
        if(orderEvent.getOrderStatus() == OrderStatus.ORDER_CREATED) {
            return Mono.fromSupplier(() -> paymentService.newOrderEvent(orderEvent));
        } else {
            return Mono.fromRunnable(() -> paymentService.cancelOrderEvent(orderEvent));
        }
    }
}
