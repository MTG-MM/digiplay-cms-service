package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.QuestTurn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestTurnRepository extends JpaRepository<QuestTurn, Long>, JpaSpecificationExecutor<QuestTurn> {
}