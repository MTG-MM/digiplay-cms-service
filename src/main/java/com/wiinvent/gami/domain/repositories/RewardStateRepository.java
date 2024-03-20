package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.reward.RewardState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardStateRepository extends JpaRepository<RewardState, Long> {


}
