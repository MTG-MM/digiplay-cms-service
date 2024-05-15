package com.wiinvent.gami.domain.repositories.transaction;

import com.wiinvent.gami.domain.entities.QuestUserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuestUserHistoryRepository extends JpaRepository<QuestUserHistory, UUID>, JpaSpecificationExecutor<QuestUserHistory> {
}
