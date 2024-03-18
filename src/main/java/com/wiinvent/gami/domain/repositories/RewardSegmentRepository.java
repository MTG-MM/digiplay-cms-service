package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.RewardSegment;
import com.wiinvent.gami.domain.entities.type.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardSegmentRepository extends JpaRepository<RewardSegment, Long> {


  List<RewardSegment> findByStatus(Status status);

  RewardSegment findByCode(String code);
}
