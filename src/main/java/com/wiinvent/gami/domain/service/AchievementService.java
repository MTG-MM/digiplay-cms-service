package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.AchievementCreateDto;
import com.wiinvent.gami.domain.dto.AchievementUpdateDto;
import com.wiinvent.gami.domain.entities.Achievement;
import com.wiinvent.gami.domain.entities.type.AchievementType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.response.AchievementResponse;
import com.wiinvent.gami.domain.utils.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class AchievementService extends BaseService{
  @Autowired
  @Lazy
  AchievementService self;

  public Page<AchievementResponse> getAchievements(String name, Status status, AchievementType achievementType, Pageable pageable) {
    Page<Achievement> achievements = achievementStorage.findAll(name, status, achievementType, pageable);
    return modelMapper.toPageAchievementResponse(achievements);
  }

  public AchievementResponse getAchievementDetail(Integer id){
    Achievement achievement = achievementStorage.findById(id);
    if (achievement == null) {
      throw new BadRequestException(Constants.ACHIEVEMENT_NOT_FOUND);
    }
    return modelMapper.toAchievementResponse(achievement);
  }

  public boolean createAchievement(AchievementCreateDto dto) {
    //map
    Achievement achievement = modelMapper.toAchievement(dto);
    //save
    try {
      self.save(achievement);
    } catch (Exception e){
      log.error("==============>createAchievement:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public boolean updateAchievement(Integer id, AchievementUpdateDto dto) {
    //validation
    Achievement achievement = achievementStorage.findById(id);
    if (achievement == null) {
      throw new BadRequestException(Constants.ACHIEVEMENT_NOT_FOUND);
    }
    modelMapper.mapAchievementUpdateDtoToAchievement(dto, achievement);
    //save
    try {
      self.save(achievement);
    } catch (Exception e){
      log.error("==============>updateAchievement:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public List<AchievementResponse> findAchievementActive() {
    List<Achievement> achievements = achievementStorage.findAllByStatus();
    return modelMapper.toListAchievementResponse(achievements);
  }

  public boolean deleteAchievement(Integer id) {
    Achievement achievement = achievementStorage.findById(id);
    if (achievement == null) {
      throw new BadRequestException(Constants.ACHIEVEMENT_NOT_FOUND);
    }
    achievement.setStatus(Status.DELETE);
    //save
    try {
      self.save(achievement);
    } catch (Exception e){
      log.error("==============>deleteAchievement:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }
  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public void save(Achievement achievement){
    achievementStorage.save(achievement);
  }
}
