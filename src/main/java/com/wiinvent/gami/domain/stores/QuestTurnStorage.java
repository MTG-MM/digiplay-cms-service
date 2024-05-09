package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Quest;
import com.wiinvent.gami.domain.entities.QuestTurn;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestTurnStorage extends BaseStorage{
  public void save(QuestTurn questTurn){
    questTurnRepository.save(questTurn);
  }

  public QuestTurn findById(Long id) {
    return questTurnRepository.findById(id).orElse(null);
  }

  public Page<QuestTurn> findAll(String name, Integer questNumber, Pageable pageable) {
    return questTurnRepository.findAll(questTurnSpecification(name, questNumber), pageable);
  }

  public Specification<QuestTurn> questTurnSpecification(String name, Integer questNumber){
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionList = new ArrayList<>();
      conditionList.add(criteriaBuilder.notEqual(root.get("status"), Status.DELETE));

      if (name != null){
        conditionList.add(criteriaBuilder.like(root.get("name"),'%'+ name + '%'));
      }
      if (questNumber != null){
        conditionList.add(criteriaBuilder.equal(root.get("questNumber"), questNumber));
      }
      return criteriaBuilder.and(conditionList.toArray(new Predicate[0]));
    };
  }
}
