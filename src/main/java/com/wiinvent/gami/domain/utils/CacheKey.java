package com.wiinvent.gami.domain.utils;

import com.wiinvent.gami.domain.entities.type.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class CacheKey {

  @Value("${redis.prefix-key-vt-game}")
  public String redisPrefixKey;

  public String genDallyLoginUser(LocalDate localDate) {
    return redisPrefixKey + ":user:daily:login:" + localDate;
  }

  public String getPeriodTypeByUser(UUID userId, Long rewardSegmentId, Long rewardItemId) {
    return redisPrefixKey + ":u:" + userId + ":rws:" + rewardSegmentId + ":rwi:" + rewardItemId;
  }

  public String getRewardPoolItemIds(Long rewardSegmentId, Long rewardItemId) {
    return redisPrefixKey + ":rws:id:" + rewardSegmentId + ":rwi:" + rewardItemId;
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

  public String genAllPaymentMethod() {
    return redisPrefixKey + ":game:payment:method:all";
  }

  public String genAllGameCategories() {
    return redisPrefixKey + ":game:category:all";
  }
  public String getGameByCategoryId(int categoryId, int pageNumber) {
    return redisPrefixKey + ":game:category:id" + categoryId + ":page:" + pageNumber;
  }

  public String getGameById(Integer gameId) {
    return redisPrefixKey + ":game:id:" + gameId ;
  }

  public String genGamePackageById(Integer packageId) {
    return redisPrefixKey + ":game:package:id:" + packageId;
  }
  public String genGamePackageByGameId(Integer gameId) {
    return redisPrefixKey + ":game:package:gameId:" + gameId;
  }

  public String genAllGameTypes() {
    return redisPrefixKey + ":game:type:all";
  }

  public String getPackageByCode(String packageCode) {
    return redisPrefixKey + ":game:package:code:" + packageCode;
  }

  public String getPackageById(Integer packageId) {
    return redisPrefixKey + ":package:id:" + packageId;
  }

  public String getPortalPackages(int pageNumber) {
    return redisPrefixKey + ":portal:package:page:" + pageNumber;
  }

  public String getPortalPackagesByTypeId(int typeId, int pageNumber) {
    return redisPrefixKey + ":portal:package:type:" + typeId + ":" + pageNumber;
  }

  public String genPackageTypeById(Integer id) {
    return redisPrefixKey + ":package:type:id:" + id;
  }

  public String getGvcPackages() {
    return redisPrefixKey + ":game:gvc:package";
  }
  public String getGvcPackageByCode(String packageCode) {
    return redisPrefixKey + ":game:gvc:package:code" + packageCode;
  }
  public String genUserSegmentById(Long userSegmentId) {
    return redisPrefixKey + ":user:segment:id:" + userSegmentId;
  }
  public String genUserSegmentDefault() {
    return redisPrefixKey + ":user:segment:default";
  }

  public String genGameTournamentById(String id) {
    return redisPrefixKey + ":game:tournament:gameId:" + id;
  }

  public String genPaymentMethodById(Integer paymentMethodId) {
    return redisPrefixKey + ":game:payment:method:id:" + paymentMethodId;
  }

  public String getCharacterById(Integer id) {
    return redisPrefixKey + ":character:id:" + id;
  }

  public String genListRewardItemByTypeAndId(ResourceType type, Long id) {
    return redisPrefixKey + ":rwi:type:" + type + ":id:" + id;
  }

  public String gemRewardItemById(Long id) {
    return redisPrefixKey + ":rwi:id:" + id;
  }

  public String genAllRewardItem() {
    return redisPrefixKey + ":reward:item:all";
  }
  public String genRewardItemStoreById(Long id) {
    return redisPrefixKey + ":rwi:store:id:" + id;
  }

  public String genKeyListBannerByBannerType(BannerType type) {
    return redisPrefixKey + ":banner:type:" + type;
  }

  public String genAchievementByType(AchievementType achievementType) {
    return redisPrefixKey + ":achievement:" + achievementType;
  }

  public String genAchievementById(Integer achievementId) {
    return redisPrefixKey + ":achievement:id" + achievementId;
  }


  public String genAllAchievement() {
    return redisPrefixKey + ":achievement:all";
  }

  public String genListAllChallenge() {
    return redisPrefixKey + ":challenge:all";
  }

  public String genChallengeById(Integer challengeId) {
    return redisPrefixKey + ":challenge:id:" + challengeId;
  }
  public String genPageActiveChallenge(int pageNumber) {
    return redisPrefixKey + ":challenge:user:id:" + pageNumber;
  }
  public String genChallengeDetailById(Integer challengeDetailId){
    return redisPrefixKey + ":challenge:id:" + challengeDetailId;
  }

  public String genAllCollections() {
    return redisPrefixKey + ":collection:all";
  }

  public String genCollectionById(Long id) {
    return redisPrefixKey + ":collection:id:" + id;
  }

  public String genListCollectionByTypeAndExternalId(CollectionType collectionType, Long id) {
    return redisPrefixKey + ":collection:type:" + collectionType + ":external:id:" + id;
  }

  public String genListCollectionByIdInAndType(List<Long> id, CollectionType collectionType) {
    return redisPrefixKey + ":collection:id:" + id + ":type:" + collectionType;
  }

  public String genListCollectionRemoveFindIn() {
    return redisPrefixKey + ":collection:remove:find:in";
  }

  public String genListRewardCollectionByUser(UUID userId) {
    return redisPrefixKey + "collection:user:id:" + userId;
  }
  public String genCollectionUserByUserId(UUID userId) {
    return redisPrefixKey + ":collection:user:id:" + userId;
  }

  public String genConfigKey(String key) {
    return redisPrefixKey.trim() + ":cf:" + key;
  }

  public String getTopChartGame(Integer typeId, int pageNumber) {
    return redisPrefixKey + ":top:chart:type:id:" + typeId + ":page:" + pageNumber;
  }

  public String getGamesByIdIn(List<Integer> ids) {
    return redisPrefixKey + "game:ids" + ids;
  }

  public String getGamesInIds() {
    return redisPrefixKey + "game:remove:ids";
  }

  public String genRewardTypeById(Long id) {
    return redisPrefixKey + ":reward:type:id:" + id;
  }

  public String genRewardSegmentByCode(String code) {
    return redisPrefixKey + ":rws:code:" + code;
  }

  public String genListRewardSegmentDetailByRewardSegmentId(Long rewardSegmentId) {
    return redisPrefixKey + ":rws:detail:" + rewardSegmentId;
  }

  public String genAllQuestTurn() {
    return redisPrefixKey + ":quest:turn:all";
  }
  public String genAllQuest(Status status) {
    return redisPrefixKey + ":quest:all:status:" + status;
  }
  public String genQuestByCode(String code) {
    return redisPrefixKey + ":quest:code:" + code;
  }

}
