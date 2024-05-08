package com.wiinvent.gami.domain.repositories.transaction;

import com.wiinvent.gami.domain.entities.transaction.LuckyPointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LuckyPointTransactionRepository extends JpaRepository<LuckyPointTransaction, UUID> {
}
