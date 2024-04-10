package com.wiinvent.gami.domain.stores.user;

import com.wiinvent.gami.domain.entities.user.UserSegment;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  public Page<UserSegment> findAll(Pageable pageable) {
    return userSegmentRepository.findAll(pageable);
  }

  public UserSegment findNextLevel(Integer currentLevel){
    return userSegmentRepository.findFirstByLevelGreaterThanOrderByLevel(currentLevel);
  }
}
