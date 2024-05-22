package com.wiinvent.gami.domain.service.user.transaction;

import com.wiinvent.gami.domain.entities.Achievement;
import com.wiinvent.gami.domain.entities.transaction.AchievementUser;
import com.wiinvent.gami.domain.pojo.AchievementInfo;
import com.wiinvent.gami.domain.response.AchievementUserResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Helper;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2

public class AchievementUserService extends BaseService {

//  public PageCursorResponse<AchievementUserResponse> getAchievementUser
//      (UUID userId, UUID transId, LocalDate startDate, LocalDate endDate, Long next, Long pre, int limit) {
//    Long startDateLong = null;
//    Long endDateLong = null;
//    if (Objects.nonNull(startDate)) startDateLong = Helper.startOfDaytoLong(startDate);
//    if (Objects.nonNull(endDate)) endDateLong = Helper.endOfDaytoLong(endDate);
//    CursorType type = CursorType.FIRST;
//    List<AchievementUser> achievementUsers = new ArrayList<>();
//    if (next == null && pre == null) {
//      next = Helper.getNowMillisAtUtc();
//      pre = 0L;
//      achievementUsers = achievementUserStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
//    } else if (pre == null){
//      type = CursorType.NEXT;
//      pre = 0L;
//      achievementUsers = achievementUserStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
//    } else if (next == null){
//      type = CursorType.PRE;
//      next = Helper.getNowMillisAtUtc();
//      achievementUsers = achievementUserStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
//      achievementUsers = achievementUsers.stream().sorted(Comparator.comparingLong(AchievementUser::getCreatedAt).reversed()).toList();
//    }
//    List<Achievement> achievements = achievementStorage.findAllByIdIn(achievementUsers.stream().map(AchievementUser::getAchievementId).toList());
//    Map<Integer, Achievement> achievementMap = achievements.stream().collect(Collectors.toMap(Achievement::getId, Function.identity()));
//    List<AchievementUserResponse> responses = modelMapper.toListAchievementUserResponse(achievementUsers);
//    responses.forEach(r -> {
//      r.setName(achievementMap.get(r.getAchievementId()).getName());
//      AchievementInfo achievementInfo = achievementMap.get(r.getAchievementId()).getAchievementInfo().stream()
//          .sorted(Comparator.comparingInt(AchievementInfo::getLevel).reversed()).filter(a -> a.getMinScore() <= r.getScore())
//          .findFirst().orElse(new AchievementInfo());
//      r.setAchievementInfo(achievementInfo);
//    });
//    return new PageCursorResponse<>(responses, limit, type, "createdAt");
//  }

  public Page<AchievementUserResponse> getAchievementUsers
      (UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
    Long startDateLong = null;
    Long endDateLong = null;
    if (Objects.nonNull(startDate)) startDateLong = Helper.startOfDaytoLong(startDate);
    if (Objects.nonNull(endDate)) endDateLong = Helper.endOfDaytoLong(endDate);
    Page<AchievementUser> achievementUsers = achievementUserStorage.findAll(userId, startDateLong, endDateLong, pageable);
    List<Integer> achievementIds = achievementUsers.getContent().stream().map(AchievementUser::getAchievementId).toList();
    Map<Integer, Achievement> achievementMap = achievementStorage.findAllByIdIn(achievementIds).stream()
        .collect(Collectors.toMap(Achievement::getId, Function.identity()));

    List<AchievementUserResponse> userCollectionResponses = modelMapper.toListAchievementUserResponse(achievementUsers.toList());
    userCollectionResponses.forEach(r -> {
//      Achievement achievement = achievementMap.getOrDefault(r.getAchievementId(), null);
//      r.setName(achievement != null ? achievement.getName() : null);
      Optional<Achievement> achievement = Optional.ofNullable(achievementMap.get(r.getAchievementId()));
      achievement.ifPresent(a -> r.setName(a.getName()));
      AchievementInfo achievementInfo = achievement.map(Achievement::getAchievementInfo)
          .orElse(Collections.emptyList())
          .stream()
          .sorted(Comparator.comparingInt(AchievementInfo::getLevel).reversed()).filter(a -> a.getMinScore() <= r.getScore())
          .findFirst().orElse(new AchievementInfo());
      r.setAchievementInfo(achievementInfo);
    });
    return new PageImpl<>(userCollectionResponses, pageable, achievementUsers.getTotalElements());
  }
}
