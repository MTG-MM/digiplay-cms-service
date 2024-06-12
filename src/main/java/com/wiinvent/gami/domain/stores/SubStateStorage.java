package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.SubState;
import com.wiinvent.gami.domain.entities.type.PackageStateType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SubStateStorage extends BaseStorage{

  public SubState findSubStateById(UUID stateId) {
    return subStateRepository.findSubStateById(stateId);
  }

  public SubState findBySubStateAndUserIdAndEndAtGreaterThan(UUID id, Long nowAtUtc) {
    return subStateRepository.findBySubStateAndUserIdAndEndAtGreaterThan(PackageStateType.ACTIVE, id, nowAtUtc);
  }
}
