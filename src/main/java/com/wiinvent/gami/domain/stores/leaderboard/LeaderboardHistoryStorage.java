package com.wiinvent.gami.domain.stores.leaderboard;

import com.wiinvent.gami.domain.entities.leaderboard.LeaderboardHistory;
import com.wiinvent.gami.domain.stores.BaseStorage;
import com.wiinvent.gami.domain.utils.RemoteCache;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class LeaderboardHistoryStorage extends BaseStorage {

  public LeaderboardHistory findByUserIdAndLeaderboardEventId(UUID userId, Long eventId) {
    String key = cacheKey.genLeaderboardUserByUserIdAndLeaderboardEventId(userId, eventId);
    LeaderboardHistory leaderboard = remoteCache.get(key, LeaderboardHistory.class);
    if(leaderboard == null) {
      leaderboard = leaderboardHistoryRepository.findByUserIdAndLeaderboardEventId(userId, eventId);
      remoteCache.put(key, leaderboard);
    }
    return leaderboard;
  }

  public void save(LeaderboardHistory leaderboard) {
    leaderboardHistoryRepository.save(leaderboard);
    remoteCache.del(removeCacheKeys(leaderboard));
  }

  private List<String> removeCacheKeys(LeaderboardHistory leaderboard) {
    List<String> cacheKeys = new ArrayList<>();
    cacheKeys.add(cacheKey.genLeaderboardUserByUserIdAndLeaderboardEventId(leaderboard.getUserId(), leaderboard.getLeaderboardEventId()));
    return cacheKeys;
  }

  public List<LeaderboardHistory> findTopUserInEventNotCache(Long id, int size) {
    return leaderboardHistoryRepository.findTopUserInEvent(id, size);
  }

  public List<LeaderboardHistory> findTopUserInEvent(Long eventId, int size) {
    String key = cacheKey.genTopUserInEvent(eventId, size);
    List<LeaderboardHistory> leaderboardHistories = remoteCache.getList(key, LeaderboardHistory.class);
    if (leaderboardHistories == null) {
      leaderboardHistories = findTopUserInEventNotCache(eventId, size);
      remoteCache.put(key, leaderboardHistories, RemoteCache.CACHE_1W_DURATION);
    }
    return leaderboardHistories;
  }

  public void updateUserNotInLeaderboard(Long eventId, List<UUID> userIds) {
    leaderboardHistoryRepository.updateUserNotInLeaderboard(eventId, userIds);
  }
}
