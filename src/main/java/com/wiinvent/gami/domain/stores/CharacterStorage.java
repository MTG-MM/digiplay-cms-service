package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Character;
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
public class CharacterStorage extends BaseStorage{
  public void save(Character character){
    characterRepository.save(character);
    remoteCache.deleteKey(cacheKey.getCharacterById(character.getId()));
  }

  public Character findById(Integer id) {
    return characterRepository.findById(id).orElse(null);
  }

  public Page<Character> findAll(String name, CharacterCategoryType categoryType, Status status, Pageable pageable) {
    return characterRepository.findAll(characterCondition(name, categoryType, status), pageable);
  }

  public Specification<Character> characterCondition(String name, CharacterCategoryType categoryType, Status status){
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionList = new ArrayList<>();
      conditionList.add(criteriaBuilder.notEqual(root.get("status"), Status.DELETE));
      if (name != null){
        conditionList.add(criteriaBuilder.like(root.get("name"), "%" + name + "%name"));
      }
      if (categoryType != null){
        conditionList.add(criteriaBuilder.equal(root.get("categoryType"), categoryType));
      }
      if (status != null){
        conditionList.add(criteriaBuilder.equal(root.get("status"), status));
      }
      return criteriaBuilder.and(conditionList.toArray(new Predicate[0]));
    };
  }
}
