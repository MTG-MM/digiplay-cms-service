package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Challenge;
import com.wiinvent.gami.domain.entities.Collection;
import com.wiinvent.gami.domain.entities.type.CollectionType;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CollectionStorage extends BaseStorage {
  public Collection findCollectionById(Long id) {
    return collectionRepository.findById(id).orElse(null);
  }

  public void save(Collection collection) {
    collectionRepository.save(collection);
    remoteCache.del(removeCacheKeys());
  }

  public Page<Collection> findAll(CollectionType type, Status status, Pageable pageable) {
    return collectionRepository.findAll(collectionSpecification(type, status), pageable);
  }

  public List<Collection> findCollectionByType() {
    return collectionRepository.findCollectionByTypeAndStatus(CollectionType.COLLECTION, Status.ACTIVE);
  }

  public List<Collection> findAllCollectionByIdIn(List<Long> ids){
    return collectionRepository.findAllCollectionByIdIn(ids);
  }
  public List<String> removeCacheKeys() {
    List<String> keys = new ArrayList<>();
    keys.add(cacheKey.genAllCollections());
    return keys;
  }

  public Specification<Collection> collectionSpecification(CollectionType type, Status status) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionLists = new ArrayList<>();
      conditionLists.add(criteriaBuilder.notEqual(root.get("status"), Status.DELETE));
      if (type != null) {
        conditionLists.add(criteriaBuilder.equal(root.get("type"), type));
      }
      if (status != null) {
        conditionLists.add(criteriaBuilder.equal(root.get("status"), status));
      }
      return criteriaBuilder.and(conditionLists.toArray(new Predicate[0]));
    };
  }
}
