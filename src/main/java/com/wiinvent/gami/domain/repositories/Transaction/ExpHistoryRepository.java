package com.wiinvent.gami.domain.repositories.Transaction;

import com.wiinvent.gami.domain.entities.Transaction.ExpHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpHistoryRepository extends JpaRepository<ExpHistory, String>
    , JpaSpecificationExecutor<ExpHistory> {
}
