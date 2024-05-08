package com.wiinvent.gami.domain.repositories.reward;

import com.wiinvent.gami.domain.entities.reward.RewardType;
import com.wiinvent.gami.domain.entities.type.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardTypeRepository extends JpaRepository<RewardType, Long>, JpaSpecificationExecutor<RewardType> {
  List<RewardType> findRewardTypeByStatus(Status status);
}
