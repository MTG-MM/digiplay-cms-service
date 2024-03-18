package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.UserSegment;
import com.wiinvent.gami.domain.stores.base.BaseStorage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserSegmentStorage extends BaseStorage {

  public List<UserSegment> findByIsDefault(Boolean isDefault) {
    return userSegmentRepository.findByIsDefault(isDefault);
  }

  public void save(UserSegment userSegment) {
    userSegmentRepository.save(userSegment);
  }

  public UserSegment findById(Long userSegmentId) {
    return userSegmentRepository.findById(userSegmentId).orElse(null);
  }
}
