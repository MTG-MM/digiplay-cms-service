package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.Task;
import com.wiinvent.gami.domain.entities.type.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {
  List<Task> findAllByIdIn(List<Integer> ids);

  List<Task> findAllByStatus(Status status);
}
