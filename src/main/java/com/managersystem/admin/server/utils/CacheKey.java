package com.managersystem.admin.server.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class CacheKey {

  @Value("${redis.prefix-key}")
  public String redisPrefixKey;

  public String getPeriodTypeByUser(UUID id, Long rewardSegmentId, Long rewardItemId) {
    return null;
  }
}
