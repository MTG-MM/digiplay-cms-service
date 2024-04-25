package com.wiinvent.gami.domain.service.user.transaction;

import com.wiinvent.gami.domain.entities.Achievement;
import com.wiinvent.gami.domain.entities.transaction.AchievementUser;
import com.wiinvent.gami.domain.pojo.AchievementInfo;
import com.wiinvent.gami.domain.response.AchievementUserResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.response.type.CursorType;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Helper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
@Service
@Log4j2

public class AchievementUserService extends BaseService {
  public PageCursorResponse<AchievementUserResponse> getAchievementUser(UUID userId, Long next, Long pre, int limit) {
    CursorType type = CursorType.FIRST;
    List<AchievementUser> achievementUsers = new ArrayList<>();
    if (next == null && pre == null) {
      next = Helper.getNowMillisAtUtc();
      pre = 0L;
      achievementUsers = achievementUserStorage.findAll(userId, next, pre, limit, type);
    } else if (pre == null){
      type = CursorType.NEXT;
      pre = 0L;
      achievementUsers = achievementUserStorage.findAll(userId, next, pre, limit, type);
    } else if (next == null){
      type = CursorType.PRE;
      next = Helper.getNowMillisAtUtc();
      achievementUsers = achievementUserStorage.findAll(userId, next, pre, limit, type);
      achievementUsers = achievementUsers.stream().sorted(Comparator.comparingLong(AchievementUser::getCreatedAt).reversed()).toList();
    }
    List<Achievement> achievements = achievementStorage.findAllByIdIn(achievementUsers.stream().map(AchievementUser::getAchievementId).toList());
    Map<Integer, Achievement> achievementMap = achievements.stream().collect(Collectors.toMap(Achievement::getId, Function.identity()));
    List<AchievementUserResponse> responses = modelMapper.toListAchievementUserResponse(achievementUsers);
    responses.forEach(r -> {
      r.setName(achievementMap.get(r.getAchievementId()).getName());
      AchievementInfo achievementInfo = achievementMap.get(r.getAchievementId()).getAchievementInfo().stream()
          .sorted(Comparator.comparingInt(AchievementInfo::getLevel).reversed()).filter(a -> a.getMinScore() <= r.getScore())
          .findFirst().orElse(new AchievementInfo());
      r.setAchievementInfo(achievementInfo);
    });
    return new PageCursorResponse<>(responses, limit, type, "createdAt");
  }}
