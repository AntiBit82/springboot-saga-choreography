package at.antonio.saga.payment.service;

import at.antonio.saga.common.dto.OrderRequestDto;
import at.antonio.saga.common.dto.PaymentRequestDto;
import at.antonio.saga.common.event.OrderEvent;
import at.antonio.saga.common.event.PaymentEvent;
import at.antonio.saga.common.event.PaymentStatus;
import at.antonio.saga.payment.entity.UserBalance;
import at.antonio.saga.payment.entity.UserTransaction;
import at.antonio.saga.payment.repo.UserBalanceRepo;
import at.antonio.saga.payment.repo.UserTransactionRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {
  @Autowired private UserBalanceRepo userBalanceRepo;

  @Autowired private UserTransactionRepo userTransactionRepo;

  @PostConstruct
  public void initUserBalanceInDb() {
      userBalanceRepo.save(new UserBalance(1,1000));
      userBalanceRepo.save(new UserBalance(2, 2000));
  }

  @Transactional
  public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
      OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();
      PaymentRequestDto paymentRequestDto = new PaymentRequestDto(
              orderRequestDto.getOrderId(),
              orderRequestDto.getUserId(),
              orderRequestDto.getAmount()
      );

      PaymentEvent paymentEvent = userBalanceRepo
              .findById(orderRequestDto.getUserId())
              .filter((ub) -> ub.getBalance() >= orderRequestDto.getAmount())
              .map(
                      ub -> {
                          ub.setBalance(ub.getBalance() - orderRequestDto.getAmount());
                          userTransactionRepo.save(
                                  new UserTransaction(
                                          orderRequestDto.getOrderId(),
                                          orderRequestDto.getUserId(),
                                          orderRequestDto.getAmount()));
                          return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
                      })
              .orElse(new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED));

      return paymentEvent;
  }

  @Transactional
  public void cancelOrderEvent(OrderEvent orderEvent) {
    userTransactionRepo
        .findById(orderEvent.getOrderRequestDto().getOrderId())
        .ifPresent(
            userTransaction -> {
              userTransactionRepo.delete(userTransaction);
              userBalanceRepo
                  .findById(userTransaction.getUserId())
                  .ifPresent(
                      userBalance -> {
                        userBalance.setBalance(userBalance.getBalance() + userTransaction.getAmount());
                      });
            });
  }
}
