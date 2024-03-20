package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.game.GamePaymentTransaction;
import com.wiinvent.gami.domain.stores.base.BaseStorage;
import org.springframework.stereotype.Component;

@Component
public class GamePaymentTransactionStorage extends BaseStorage {


  public void save(GamePaymentTransaction transaction) {
    gamePaymentTransactionRepository.save(transaction);
  }

  public boolean existsGamePaymentTransactionsByPaymentTransactionId(String paymentTransactionId) {
    return gamePaymentTransactionRepository.existsGamePaymentTransactionsByPaymentTransactionId(paymentTransactionId);
  }
}
