package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.Quest;
import com.wiinvent.gami.domain.entities.type.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long>, JpaSpecificationExecutor<Quest> {
  List<Quest> findAllByIdIn(List<Long> ids);

  List<Quest> findAllByStatus(Status status);
}