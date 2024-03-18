package com.wiinvent.gami.domain.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class CacheKey {

  @Value("${redis.prefix-key}")
  public String redisPrefixKey;

  public String getPeriodTypeByUser(UUID userId, Long rewardSegmentId, Long rewardItemId) {
    return redisPrefixKey + ":u:" + userId + ":rws:" + rewardSegmentId + ":rwi:" + rewardItemId;
  }

  public String getRewardPoolItemIds(Long rewardSegmentId, Long rewardItemId) {
    return redisPrefixKey + ":rws:" + rewardSegmentId + ":rwi:" + rewardItemId;
  }

  public String getStatisticRewardPoolItemIds(LocalDate day, Long rewardSegmentId, Long rewardItemId) {
    return redisPrefixKey + ":statistic:" + day + ":rws:" + rewardSegmentId + ":rwi:" + rewardItemId;
  }

  public String getUserById(UUID id) {
    return redisPrefixKey + ":u:id" + id;
  }

  public String getUserByUsername(String username) {
    return redisPrefixKey + ":u:name" + username;
  }
}
