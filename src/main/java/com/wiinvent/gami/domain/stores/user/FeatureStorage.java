package com.wiinvent.gami.domain.stores.user;

import com.wiinvent.gami.domain.entities.Character;
import com.wiinvent.gami.domain.entities.type.CharacterCategoryType;
import com.wiinvent.gami.domain.entities.type.FeatureCode;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.user.Feature;
import com.wiinvent.gami.domain.stores.BaseStorage;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeatureStorage extends BaseStorage {
  public Page<Feature> findAll(String name, FeatureCode featureCode, Pageable pageable){
    return featureRepository.findAll(featureCondition(name, featureCode), pageable);
  }
  public Specification<Feature> featureCondition(String name, FeatureCode featureCode){
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionList = new ArrayList<>();
      conditionList.add(criteriaBuilder.notEqual(root.get("status"), Status.DELETE));
      if (name != null){
        conditionList.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
      }
      if (featureCode != null){
        conditionList.add(criteriaBuilder.equal(root.get("code"), featureCode));
      }
      return criteriaBuilder.and(conditionList.toArray(new Predicate[0]));
    };
  }
  public Feature findById(Integer id){
    return featureRepository.findById(id).orElse(null);
  }

  public Feature save(Feature feature){
    return featureRepository.save(feature);
  }
}