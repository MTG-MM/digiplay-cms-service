package com.wiinvent.gami.domain.utils;

import com.wiinvent.gami.domain.entities.type.BannerType;
import com.wiinvent.gami.domain.entities.type.ResourceType;
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
  public String getGameByCategoryId(int categoryId, int pageNumber) {
    return redisPrefixKeyVtGame + ":game:category:id" + categoryId + ":page:" + pageNumber;

  }

  public String getGameById(Integer gameId) {
    return redisPrefixKeyVtGame + ":game:id:" + gameId ;
  }

  public String genGamePackageById(Integer packageId) {
    return redisPrefixKeyVtGame + ":game:package:id:" + packageId;
  }
  public String genGamePackageByGameId(Integer gameId) {
    return redisPrefixKeyVtGame + ":game:package:gameId:" + gameId;
  }

  public String getPackageByCode(String packageCode) {
    return redisPrefixKeyVtGame + ":game:package:code:" + packageCode;
  }

  public String getGvcPackages() {
    return redisPrefixKeyVtGame + ":game:gvc:package";
  }
  public String getGvcPackageByCode(String packageCode) {
    return redisPrefixKeyVtGame + ":game:gvc:package:code" + packageCode;
  }
  public String genUserSegmentById(Long userSegmentId) {
    return redisPrefixKeyVtGame + ":user:segment:id:" + userSegmentId;
  }
  public String genUserSegmentDefault() {
    return redisPrefixKeyVtGame + ":user:segment:default";
  }

  public String genGameTournamentById(String id) {
    return redisPrefixKeyVtGame + ":game:tournament:gameId:" + id;
  }

  public String genPaymentMethodById(Integer paymentMethodId) {
    return redisPrefixKeyVtGame + ":game:payment:method:id:" + paymentMethodId;
  }

  public String getCharacterById(Integer id) {
    return redisPrefixKeyVtGame + ":character:id:" + id;
  }

  public String genListRewardItemByTypeAndId(ResourceType type, Long id) {
    return redisPrefixKeyVtGame + ":rwi:type:" + type + ":id:" + id;
  }

  public String gemRewardItemById(Long id) {
    return redisPrefixKeyVtGame + ":rwi:id:" + id;
  }

  public String genAllRewardItem() {
    return redisPrefixKeyVtGame + ":reward:item:all";
  }
  public String genRewardItemStoreById(Long id) {
    return redisPrefixKeyVtGame + ":rwi:store:id:" + id;
  }

  public String genKeyListBannerByBannerType(BannerType type) {
    return redisPrefixKeyVtGame + ":banner:type:" + type;
  }
}
