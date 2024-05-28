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
    remoteCache.del(removeCacheKey(character));
  }

  List<String> removeCacheKey(Character character) {
    List<String> removeKeys = new ArrayList<>();
    removeKeys.add(cacheKey.genKeyCharacterByCharacterId(character.getId()));
    removeKeys.add(cacheKey.genALlDefaultCharacter());
    removeKeys.add(cacheKey.genALlCharacter());
    removeKeys.add(cacheKey.genCharacterByExternalId(character.getExternalId()));
    removeKeys.add(cacheKey.genKeyPageCharacterByPageNumber(0, character.getCategoryType(), character.getType(), character.getGender()));
    removeKeys.add(cacheKey.genKeyPageCharacterByPageNumber(1, character.getCategoryType(), character.getType(), character.getGender()));
    removeKeys.add(cacheKey.genKeyPageCharacterByPageNumber(2, character.getCategoryType(), character.getType(), character.getGender()));
    removeKeys.add(cacheKey.genKeyPageCharacterByPageNumber(3, character.getCategoryType(), character.getType(), character.getGender()));
    removeKeys.add(cacheKey.genKeyPageCharacterByPageNumber(4, character.getCategoryType(), character.getType(), character.getGender()));
    return removeKeys;
  }

  public Character findById(Integer id) {
    return characterRepository.findById(id).orElse(null);
  }

  public Page<Character> findAll(String name, CharacterCategoryType categoryType, Status status, Pageable pageable) {
    return characterRepository.findAll(characterCondition(name, categoryType, status), pageable);
  }

  public List<Character> findAllByIdIn(List<Integer> ids){
    return characterRepository.findAllByIdIn(ids);
  }

  public List<Character> findAllCharacter(CharacterCategoryType categoryType){
    return characterRepository.findAll(characterActiveCondition(categoryType));
  }

  public Specification<Character> characterActiveCondition(CharacterCategoryType categoryType){
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionList = new ArrayList<>();
      conditionList.add(criteriaBuilder.equal(root.get("status"), Status.ACTIVE));
      if (categoryType != null){
        conditionList.add(criteriaBuilder.equal(root.get("categoryType"), categoryType));
      }
      return criteriaBuilder.and(conditionList.toArray(new Predicate[0]));
    };
  }

  public Specification<Character> characterCondition(String name, CharacterCategoryType categoryType, Status status){
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionList = new ArrayList<>();
      conditionList.add(criteriaBuilder.notEqual(root.get("status"), Status.DELETE));
      if (name != null){
        conditionList.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
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
