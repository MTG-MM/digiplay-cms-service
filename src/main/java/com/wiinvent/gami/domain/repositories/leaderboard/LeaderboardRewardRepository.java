package com.wiinvent.gami.domain.repositories.leaderboard;

import com.wiinvent.gami.domain.entities.leaderboard.LeaderboardReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LeaderboardRewardRepository extends JpaRepository<LeaderboardReward, Long>, JpaSpecificationExecutor<LeaderboardReward> {
  List<LeaderboardReward> findByCode(String leaderboardCode);
}