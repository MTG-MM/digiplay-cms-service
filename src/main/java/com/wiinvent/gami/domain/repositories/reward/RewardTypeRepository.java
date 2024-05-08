package com.wiinvent.gami.domain.repositories.reward;

import com.wiinvent.gami.domain.entities.reward.RewardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardTypeRepository extends JpaRepository<RewardType, Long>, JpaSpecificationExecutor<RewardType> {
}
