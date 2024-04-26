package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.SubState;
import com.wiinvent.gami.domain.entities.type.PackageStateType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class SubStateStorage extends BaseStorage{
  public List<SubState> findBySubStateAndUserIdInAndEndAtGreaterThan(List<UUID> userId, Long endAtNow){
    return subStateRepository.findBySubStateAndUserIdInAndEndAtGreaterThan(PackageStateType.ACTIVE, userId, endAtNow);
  }
}
