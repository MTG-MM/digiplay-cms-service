package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.GamePaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePaymentTransactionRepository extends JpaRepository<GamePaymentTransaction, String> {
  boolean existsGamePaymentTransactionsByPaymentTransactionId(String paymentTransactionId);
}