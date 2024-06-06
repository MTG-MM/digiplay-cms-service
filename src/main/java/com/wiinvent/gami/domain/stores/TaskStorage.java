package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Task;
import com.wiinvent.gami.domain.entities.type.DailyTaskType;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskStorage extends BaseStorage {
  public void save(Task task) {
    taskRepository.save(task);
    remoteCache.del(cacheKey.genTaskById(task.getId()));
    remoteCache.del(cacheKey.findAllTaskByStatus(task.getStatus()));
  }

  public Task findById(Integer id) {
    return taskRepository.findById(id).orElse(null);
  }

  public List<Task> findAllByIdIn(List<Integer> ids) {
    return taskRepository.findAllByIdIn(ids);
  }

  public Page<Task> findAll(String name, DailyTaskType type, Pageable pageable) {
    return taskRepository.findAll(taskSpecification(name, type), pageable);
  }

  public Specification<Task> taskSpecification(String name, DailyTaskType type) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionList = new ArrayList<>();
      conditionList.add(criteriaBuilder.notEqual(root.get("status"), Status.DELETE));
      if (name != null) {
        conditionList.add(criteriaBuilder.like(root.get("name"), '%' + name + '%'));
      }
      if (type != null) {
        conditionList.add(criteriaBuilder.equal(root.get("type"), type));
      }
      return criteriaBuilder.and(conditionList.toArray(new Predicate[0]));
    };
  }

  public List<Task> findAllByStatus() {
    return taskRepository.findAllByStatus(Status.ACTIVE);
  }
}
