package com.wiinvent.gami.domain.repositories.user;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.user.UserSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSegmentRepository extends JpaRepository<UserSegment, Long>, JpaSpecificationExecutor<UserSegment> {

  List<UserSegment> findByIsDefault(Boolean isDefault);
  UserSegment findFirstByLevelGreaterThanOrderByLevelAsc(Integer level);
  UserSegment findUserSegmentByLevel(Integer level);
  List<UserSegment> findUserSegmentsByStatusIn(List<Status> statuses);

  List<UserSegment> findByIdIn(List<Long> ids);
}
