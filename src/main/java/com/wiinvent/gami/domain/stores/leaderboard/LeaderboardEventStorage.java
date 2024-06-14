package com.wiinvent.gami.domain.stores.leaderboard;

import com.wiinvent.gami.domain.entities.leaderboard.LeaderboardEvent;
import com.wiinvent.gami.domain.entities.type.RewardStateType;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LeaderboardEventStorage extends BaseStorage {
  public LeaderboardEvent findCurrentEvent(long nowAtUtc) {
    String key = cacheKey.genCurrentLeaderboard();
    LeaderboardEvent event = remoteCache.get(key, LeaderboardEvent.class);
    if(event == null) {
      event = leaderboardEventRepository.findByStartAtGreaterThanEqualAndEndAtLessThan(nowAtUtc, nowAtUtc);
      if(event != null) {
        remoteCache.putExpireMillis(key, event, event.getEndAt() - nowAtUtc);
      }
    }
    return event;
  }

  public List<LeaderboardEvent> findByEndAtLessThanAndRewardStatus(long nowAtUtc, RewardStateType rewardState) {
    return leaderboardEventRepository.findByEndAtLessThanAndRewardStatus(nowAtUtc, rewardState);
  }

  public void save(LeaderboardEvent event) {
    leaderboardEventRepository.save(event);
    remoteCache.del(removeCacheKeys(event));
  }

  private List<String> removeCacheKeys(LeaderboardEvent event) {
    List<String> removeKeys = new ArrayList<>();
    removeKeys.add(cacheKey.genCurrentLeaderboard());
    return removeKeys;
  }
}
