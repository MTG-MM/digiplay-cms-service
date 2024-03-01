package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.RewardItemStatistic;
import com.managersystem.admin.server.entities.RewardSegment;
import com.managersystem.admin.server.entities.type.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RewardSegmentRepository extends JpaRepository<RewardSegment, Long> {


  List<RewardSegment> findByStatus(Status status);

  RewardSegment findByCode(String code);
}
