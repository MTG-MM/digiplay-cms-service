package com.wiinvent.gami.domain.stores.leaderboard;

import com.wiinvent.gami.domain.entities.leaderboard.LeaderboardReward;

import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LeaderboardRewardStorage extends BaseStorage {
  public List<LeaderboardReward> findByCode(String leaderboardCode) {
    return leaderboardRewardRepository.findByCode(leaderboardCode);
  }
}
