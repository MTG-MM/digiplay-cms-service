package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.CollectionCreateDto;
import com.wiinvent.gami.domain.dto.CollectionUpdateDto;
import com.wiinvent.gami.domain.entities.Collection;
import com.wiinvent.gami.domain.entities.type.CollectionType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.response.CollectionResponse;
import com.wiinvent.gami.domain.utils.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class CollectionService extends BaseService{
  @Autowired
  @Lazy
  CollectionService self;

  public Page<CollectionResponse> getCollection(CollectionType collectionType, Status status, Pageable pageable) {
    Page<Collection> collections = collectionStorage.findAll(collectionType, status, pageable);
    return modelMapper.toPageCollectionResponse(collections);
  }

  public CollectionResponse getCollectionDetail(Long id){
    Collection collection = collectionStorage.findCollectionById(id);
    if (collection == null) {
      throw new BadRequestException(Constants.COLLECTION_NOT_FOUND);
    }
    return modelMapper.toCollectionResponse(collection);
  }

  public List<CollectionResponse> getCollectionsInTypeCollection() {
    List<Collection> collections = collectionStorage.findCollectionByType();
    return modelMapper.toListCollectionResponse(collections);
  }

  public boolean createCollection(CollectionCreateDto dto) {
    if(Objects.isNull(dto.getStatus())) dto.setStatus(Status.ACTIVE);
    Collection collection = modelMapper.toCollection(dto);
    //save
    try {
      self.save(collection);
    } catch (Exception e){
      log.error("==============>createCollection:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public boolean updateCollection(Long id, CollectionUpdateDto dto) {
    Collection collection = collectionStorage.findCollectionById(id);
    if (collection == null) {
      throw new BadRequestException(Constants.COLLECTION_NOT_FOUND);
    }
    modelMapper.mapCollectionUpdateDtoToCollection(dto, collection);
    //save
    try {
      self.save(collection);
    } catch (Exception e){
      log.error("==============>updateCollection:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public boolean deleteCollection(Long id) {
    Collection collection = collectionStorage.findCollectionById(id);
    if (collection == null) {
      throw new BadRequestException(Constants.COLLECTION_NOT_FOUND);
    }
    collection.setStatus(Status.DELETE);
    //save
    try {
      self.save(collection);
    } catch (Exception e){
      log.error("==============>deleteCollection:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }
  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public void save(Collection collection){
    collectionStorage.save(collection);
  }
}
