package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.Achievement;
import com.wiinvent.gami.domain.entities.type.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Integer> , JpaSpecificationExecutor<Achievement> {
  List<Achievement> findAllByIdIn(List<Integer> ids);

  List<Achievement> findAllByStatus(Status status);
}
