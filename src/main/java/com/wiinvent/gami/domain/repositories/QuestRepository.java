package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long>, JpaSpecificationExecutor<Quest> {
  List<Quest> findAllByIdIn(List<Long> ids);
}