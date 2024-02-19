package com.managersystem.admin.server.stores;

import com.managersystem.admin.server.entities.UserSegment;
import com.managersystem.admin.server.stores.base.BaseStorage;
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
