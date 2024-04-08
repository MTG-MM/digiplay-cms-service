package com.wiinvent.gami.domain.repositories.transaction;

import com.wiinvent.gami.domain.entities.transaction.CoinTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinTransactionRepository extends JpaRepository<CoinTransaction, String>
    , JpaSpecificationExecutor<CoinTransaction> {
}
