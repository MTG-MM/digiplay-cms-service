package com.wiinvent.gami.domain.repositories.leaderboard;

import com.wiinvent.gami.domain.entities.leaderboard.LeaderboardHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LeaderboardHistoryRepository extends JpaRepository<LeaderboardHistory, UUID>, JpaSpecificationExecutor<LeaderboardHistory> {
  LeaderboardHistory findByUserIdAndLeaderboardEventId(UUID userId, Long eventId);

  @Query(nativeQuery = true, value = "SELECT * FROM leaderboard_history lh " +
      "where lh.leaderboard_event_id = ?1 order by lh.point DESC limit : ?2")
  List<LeaderboardHistory> findTopUserInEvent(Long id, int size);

  @Modifying
  @Query(nativeQuery = true, value = "UPDATE leaderboard_history l set l.status = 'QUALIFY' where l.id = ?1 and l.user_id not in ?2")
  void updateUserNotInLeaderboard(Long eventId, List<UUID> userIds);
}