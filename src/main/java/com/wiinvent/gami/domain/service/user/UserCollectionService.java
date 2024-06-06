package com.wiinvent.gami.domain.service.user;

import com.wiinvent.gami.domain.entities.Collection;
import com.wiinvent.gami.domain.entities.user.UserCollection;
import com.wiinvent.gami.domain.response.UserCollectionResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Helper;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserCollectionService extends BaseService {
  public Page<UserCollectionResponse> getUserCollections
      (UUID userId, UUID transId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
    Long startDateLong = null;
    Long endDateLong = null;
    if (Objects.nonNull(startDate)) startDateLong = Helper.startOfDaytoLong(startDate);
    if (Objects.nonNull(endDate)) endDateLong = Helper.endOfDaytoLong(endDate);
    Page<UserCollection> userCollections = userCollectionStorage.findAll(userId, transId, startDateLong, endDateLong, pageable);
    List<Long> collectionIds = userCollections.getContent().stream().map(UserCollection::getCollectionId).toList();
    Map<Long, Collection> userCollectionMap = collectionStorage.findAllCollectionByIdIn(collectionIds).stream()
        .collect(Collectors.toMap(Collection::getId, Function.identity()));
    List<UserCollectionResponse> userCollectionResponses = new ArrayList<>();
    for (UserCollection userCollection : userCollections.getContent()) {
      UserCollectionResponse collectionResponse = modelMapper.toUserCollectionResponse(userCollection);
      Collection collection = userCollectionMap.get(userCollection.getCollectionId());
      if (collection == null){
        collectionResponse.setCollectionName("Deleted collection");
      } else {
        collectionResponse.setCollectionName(collection.getName());
      }
      userCollectionResponses.add(collectionResponse);
    }
    return new PageImpl<>(userCollectionResponses, pageable, userCollections.getTotalElements());
  }
}
