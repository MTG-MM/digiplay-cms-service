package com.wiinvent.gami.domain.repositories.transaction;

import com.wiinvent.gami.domain.entities.transaction.CharacterUserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterUserTransactionRepository extends JpaRepository<CharacterUserTransaction, String>
    , JpaSpecificationExecutor<CharacterUserTransaction> {
}