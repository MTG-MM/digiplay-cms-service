package com.wiinvent.gami.domain.repositories.leaderboard;

import com.wiinvent.gami.domain.entities.leaderboard.LeaderboardEvent;
import com.wiinvent.gami.domain.entities.reward.RewardState;
import com.wiinvent.gami.domain.entities.type.RewardStateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeaderboardEventRepository extends JpaRepository<LeaderboardEvent, Long>, JpaSpecificationExecutor<LeaderboardEvent> {
  @Query(value = "SELECT e FROM LeaderboardEvent e where e.startAt <= ?1 and e.endAt > ?2")
  LeaderboardEvent findByStartAtGreaterThanEqualAndEndAtLessThan(Long startAt, Long endAt);

  List<LeaderboardEvent> findByEndAtLessThanAndRewardStatus(long nowAtUtc, RewardStateType rewardState);
}