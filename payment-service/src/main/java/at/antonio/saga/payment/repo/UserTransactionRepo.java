package at.antonio.saga.payment.repo;

import at.antonio.saga.payment.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTransactionRepo extends JpaRepository<UserTransaction, Integer> {}
