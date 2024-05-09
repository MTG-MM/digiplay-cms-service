package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Character;
import com.wiinvent.gami.domain.entities.Quest;
import com.wiinvent.gami.domain.entities.type.CharacterCategoryType;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestStorage extends BaseStorage{
  public void save(Quest quest){
    questRepository.save(quest);
  }

  public Quest findById(Long id) {
    return questRepository.findById(id).orElse(null);
  }

  public Page<Quest> findAll(String code, String name, Integer gameId, Pageable pageable) {
    return questRepository.findAll(questSpecification(code, name, gameId), pageable);
  }

  public Specification<Quest> questSpecification(String code, String name, Integer gameId){
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionList = new ArrayList<>();
      conditionList.add(criteriaBuilder.notEqual(root.get("status"), Status.DELETE));
      if (code != null){
        conditionList.add(criteriaBuilder.equal(root.get("code"), code));
      }
      if (name != null){
        conditionList.add(criteriaBuilder.like(root.get("name"),'%'+ name + '%'));
      }
      if (gameId != null){
        conditionList.add(criteriaBuilder.equal(root.get("gameId"), gameId));
      }
      return criteriaBuilder.and(conditionList.toArray(new Predicate[0]));
    };
  }
}
