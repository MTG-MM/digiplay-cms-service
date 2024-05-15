package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.QuestTurn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestTurnRepository extends JpaRepository<QuestTurn, Long>, JpaSpecificationExecutor<QuestTurn> {
  List<QuestTurn> findAllByIdIn(List<Long> ids);
}