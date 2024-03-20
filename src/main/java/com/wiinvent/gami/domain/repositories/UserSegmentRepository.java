package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.user.UserSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSegmentRepository extends JpaRepository<UserSegment, Long> {

  List<UserSegment> findByIsDefault(Boolean isDefault);
}
