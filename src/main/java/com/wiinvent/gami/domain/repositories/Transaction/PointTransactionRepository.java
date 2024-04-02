package com.wiinvent.gami.domain.repositories.Transaction;

import com.wiinvent.gami.domain.entities.Transaction.CoinTransaction;
import com.wiinvent.gami.domain.entities.Transaction.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PointTransactionRepository extends JpaRepository<PointTransaction, String>
    , JpaSpecificationExecutor<PointTransaction> {
}