package com.wiinvent.gami.domain.repositories.payment;

import com.wiinvent.gami.domain.entities.payment.PaymentTransaction;
import com.wiinvent.gami.domain.entities.reward.RewardItemHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, String>, JpaSpecificationExecutor<PaymentTransaction> {
}