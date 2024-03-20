package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.reward.RewardItemHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RewardItemHistoryRepository extends JpaRepository<RewardItemHistory, UUID> {


  @Query("SELECT COUNT(DISTINCT userId) FROM RewardItemHistory " +
      "WHERE rewardSegmentId = :rewardSegmentId " +
      "AND rewardItemId = :rewardItemId " +
      "AND createdAt >= :startDateAtVn " +
      "AND createdAt < :endDateAtVn")
  Integer countUsersInCreatedAtBetween(
      @Param("rewardSegmentId") Long rewardSegmentId,
      @Param("rewardItemId") Long rewardItemId,
      @Param("startDateAtVn") long startDateAtVn,
      @Param("endDateAtVn") long endDateAtVn);

  @Query("SELECT COUNT(*) FROM RewardItemHistory " +
      "WHERE rewardSegmentId = :rewardSegmentId " +
      "AND rewardItemId = :rewardItemId " +
      "AND createdAt >= :startDateAtVn " +
      "AND createdAt < :endDateAtVn " +
      "GROUP BY rewardSegmentId, rewardItemId")
  Integer countRewardItemReceivedInCreatedAtBetween(
      @Param("rewardSegmentId") Long rewardSegmentId,
      @Param("rewardItemId") Long rewardItemId,
      @Param("startDateAtVn") long startDateAtVn,
      @Param("endDateAtVn") long endDateAtVn);
}
