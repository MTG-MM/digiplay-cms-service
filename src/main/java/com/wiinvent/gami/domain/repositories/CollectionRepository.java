package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.Collection;
import com.wiinvent.gami.domain.entities.type.CollectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long>, JpaSpecificationExecutor<Collection> {
  List<Collection> findCollectionByType(CollectionType collectionType);

  List<Collection> findAllCollectionByIdIn(List<Long> ids);
}
