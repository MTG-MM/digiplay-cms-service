package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.CollectionCreateDto;
import com.wiinvent.gami.domain.dto.CollectionUpdateDto;
import com.wiinvent.gami.domain.entities.Collection;
import com.wiinvent.gami.domain.entities.reward.RewardItem;
import com.wiinvent.gami.domain.entities.type.CollectionType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import com.wiinvent.gami.domain.response.CollectionInTypeResponse;
import com.wiinvent.gami.domain.response.CollectionResponse;
import com.wiinvent.gami.domain.utils.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CollectionService extends BaseService {
  @Autowired
  @Lazy
  CollectionService self;

  public Page<CollectionResponse> getCollection(CollectionType collectionType, Status status, Pageable pageable) {
    Page<Collection> collections = collectionStorage.findAll(collectionType, status, pageable);
    Page<CollectionResponse> collectionResponses = modelMapper.toPageCollectionResponse(collections);
    List<Collection> allCollection = collectionStorage.findCollectionByType(CollectionType.PIECE);
    Map<Long, Collection> pieceCollectionMap = allCollection.stream().collect(Collectors.toMap(Collection::getId, Function.identity()));
    List<CollectionResponse> result = new ArrayList<>();
    for (Collection collection : collections.getContent()) {
      List<UserRewardItems> userRewardItem = getRewardItemInfo(collection);
      CollectionResponse collectionResponse = collectionResponses.getContent().stream()
          .filter(c -> c.getId().equals(collection.getId()))
          .findFirst()
          .orElseThrow(() -> new ResourceNotFoundException("CollectionResponse not found for Collection id: " + collection.getId()));
      collectionResponse.setRewardItems(userRewardItem);
      collectionResponse.setCollectionPiece(getCollectionPiece(collection.getCollectionPieces(), pieceCollectionMap));
      result.add(collectionResponse);
    }

    return new PageImpl<>(result, pageable, collectionResponses.getTotalElements());
  }

  public List<UserRewardItems> getCollectionPiece(List<Long> collectionPieceIds, Map<Long, Collection> pieceCollectionMap) {
    List<UserRewardItems> userRewardItems = new ArrayList<>();
    if(collectionPieceIds == null) {
      return new ArrayList<>();
    }
    for (Long collectionPieceId : collectionPieceIds) {
      UserRewardItems userRewardItem = new UserRewardItems();
      Collection collectionPiece = pieceCollectionMap.get(collectionPieceId);
      if (collectionPiece == null) {
        continue;
      }
      userRewardItem.setId(collectionPiece.getId());
      userRewardItem.setRewardName(collectionPiece.getName());
      userRewardItems.add(userRewardItem);
    }
    return userRewardItems;
  }

  public CollectionResponse getCollectionDetail(Long id) {
    List<Collection> allCollection = collectionStorage.findCollectionByType(CollectionType.PIECE);
    Map<Long, Collection> pieceCollectionMap = allCollection.stream().collect(Collectors.toMap(Collection::getId, Function.identity()));
    Collection collection = collectionStorage.findCollectionById(id);
    if (collection == null) {
      throw new BadRequestException(Constants.COLLECTION_NOT_FOUND);
    }
    List<UserRewardItems> userRewardItems = getRewardItemInfo(collection);
    CollectionResponse collectionResponse = modelMapper.toCollectionResponse(collection);
    collectionResponse.setRewardItems(userRewardItems);
    collectionResponse.setCollectionPiece(getCollectionPiece(collection.getCollectionPieces(), pieceCollectionMap));
    return collectionResponse;
  }

  public List<UserRewardItems> getRewardItemInfo(Collection collection) {
    if (collection.getExternalId() == null) {
      return Collections.emptyList();
    }
    List<UserRewardItems> userRewardItems = new ArrayList<>();
    UserRewardItems userRewardItem = new UserRewardItems();
    if (collection.getType() == CollectionType.PIECE) {
      Collection collectionPiece = collectionStorage.findCollectionById(collection.getExternalId());
      userRewardItem.setId(collectionPiece.getId());
      userRewardItem.setRewardName(collectionPiece.getName());
      userRewardItems.add(userRewardItem);
    } else {
      RewardItem rewardItem = rewardItemStorage.findById(collection.getExternalId());
      if (rewardItem == null) {
        return Collections.emptyList();
      }
      userRewardItem.setId(rewardItem.getId());
      userRewardItem.setRewardName(rewardItem.getRewardName());
      userRewardItems.add(userRewardItem);
    }
    return userRewardItems;
  }

  public List<CollectionInTypeResponse> getCollectionsInTypeCollection(CollectionType collectionType) {
    List<Collection> collections = collectionStorage.findCollectionByType(collectionType);
    List<CollectionInTypeResponse> collectionInTypeResponses = new ArrayList<>();
    for (Collection collection : collections) {
      CollectionInTypeResponse collectionInTypeResponse = new CollectionInTypeResponse();
      collectionInTypeResponse.toResponse(collection);
      collectionInTypeResponses.add(collectionInTypeResponse);
    }
    return collectionInTypeResponses;
  }

  public boolean createCollection(CollectionCreateDto dto) {
    Collection collection = modelMapper.toCollection(dto);
    setRewardItemInCollection(dto, collection);
    //save
    try {
      self.save(collection);
    } catch (Exception e) {
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
    setRewardItemInCollection(dto, collection);
    //save
    try {
      self.save(collection);
    } catch (Exception e) {
      log.error("==============>updateCollection:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  private void setRewardItemInCollection(CollectionUpdateDto dto, Collection collection) {
    if (dto.getRewardItems() != null && !dto.getRewardItems().isEmpty()) {
      collection.setExternalId(dto.getRewardItems().getFirst().getId());
    }
    if(dto.getCollectionPiece() != null && !dto.getCollectionPiece().isEmpty()) {
      List<Long> listCollectionPiece = dto.getCollectionPiece().stream().map(UserRewardItems::getId).toList();
      collection.setCollectionPieces(listCollectionPiece);
    }
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
    } catch (Exception e) {
      log.error("==============>deleteCollection:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public void save(Collection collection) {
    collectionStorage.save(collection);
  }
}
