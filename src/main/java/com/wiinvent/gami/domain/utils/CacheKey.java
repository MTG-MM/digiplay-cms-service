package com.wiinvent.gami.domain.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class CacheKey {

  @Value("${redis.prefix-key-vt-game}")
  public String redisPrefixKeyVtGame;

  public String getPeriodTypeByUser(UUID userId, Long rewardSegmentId, Long rewardItemId) {
    return redisPrefixKeyVtGame + ":u:" + userId + ":rws:" + rewardSegmentId + ":rwi:" + rewardItemId;
  }

  public String getRewardPoolItemIds(Long rewardSegmentId, Long rewardItemId) {
    return redisPrefixKeyVtGame + ":rws:" + rewardSegmentId + ":rwi:" + rewardItemId;
  }

  public String getStatisticRewardPoolItemIds(LocalDate day, Long rewardSegmentId, Long rewardItemId) {
    return redisPrefixKeyVtGame + ":statistic:" + day + ":rws:" + rewardSegmentId + ":rwi:" + rewardItemId;
  }

  public String getUserById(UUID id) {
    return redisPrefixKeyVtGame + ":u:id" + id;
  }

  public String getUserByUsername(String username) {
    return redisPrefixKeyVtGame + ":u:name" + username;
  }

  public String genAllPaymentMethod() {
    return redisPrefixKeyVtGame + ":game:payment:method:all";
  }

  public String genAllGameCategories() {
    return redisPrefixKeyVtGame + ":game:category:all";
  }

  public String getGameById(Integer gameId) {
    return redisPrefixKeyVtGame + ":game:id:" + gameId ;
  }
  public String genUserSegmentById(Long userSegmentId) {
    return redisPrefixKeyVtGame + ":user:segment:id:" + userSegmentId;
  }
  public String genUserSegmentDefault() {
    return redisPrefixKeyVtGame + ":user:segment:default";
  }
  public String genGamePackageByGameId(int gameId) {
    return redisPrefixKeyVtGame + ":game:package:gameId:" + gameId;
  }

  public String genGameTournamentById(String id) {
    return redisPrefixKeyVtGame + ":game:tournament:gameId:" + id;
  }

  public String genPaymentMethodById(Integer paymentMethodId) {
    return redisPrefixKeyVtGame + ":game:payment:method:id:" + paymentMethodId;
  }
  public String genGamePackageById(Integer packageId) {
    return redisPrefixKeyVtGame + ":game:package:id:" + packageId;
  }
  public String getPackageByCode(String packageCode) {
    return redisPrefixKeyVtGame + ":game:package:code:" + packageCode;
  }
}
