package at.antonio.saga.order.config;

import at.antonio.saga.common.event.PaymentEvent;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConsumerConfig {
  @Autowired private OrderStatusUpdateHandler orderStatusUpdateHandler;

  @Bean
  public Consumer<PaymentEvent> paymentEventConsumer() {
    return (payment) ->
        orderStatusUpdateHandler.updateOrder(
            payment.getPaymentRequestDto().getOrderId(),
            po -> {
              po.setPaymentStatus(payment.getPaymentStatus());
            });
  }
}
