package at.antonio.saga.payment.repo;

import at.antonio.saga.payment.entity.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBalanceRepo extends JpaRepository<UserBalance, Integer> {}
