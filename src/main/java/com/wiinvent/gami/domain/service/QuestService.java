package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.QuestCreateDto;
import com.wiinvent.gami.domain.dto.QuestUpdateDto;
import com.wiinvent.gami.domain.entities.Quest;
import com.wiinvent.gami.domain.entities.game.Game;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.response.QuestResponse;
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

import java.util.Objects;

@Service
@Log4j2
public class QuestService extends BaseService {
  @Autowired
  @Lazy
  QuestService self;

  public Page<QuestResponse> findAll(String code, String name, Integer gameId, Pageable pageable){
    Page<Quest> quests = questStorage.findAll(code, name, gameId, pageable);
    Page<QuestResponse> questResponses = modelMapper.toPageQuestResponse(quests);
    for (QuestResponse questResponse : questResponses.getContent()) {
      Game game = gameStorage.findById(questResponse.getGameId());
      if (game != null) {
        questResponse.setGameName(game.getName());
      }
    }
    return questResponses;
  }

  public QuestResponse getQuestDetail(Long id){
    Quest quest = questStorage.findById(id);
    if (quest == null) {
      throw new BadRequestException(Constants.QUEST_NOT_FOUND);
    }
    QuestResponse questResponse = modelMapper.toQuestResponse(quest);
    Game game = gameStorage.findById(quest.getGameId());
    if (game != null) {
      questResponse.setGameName(game.getName());
    }
    return questResponse;
  }

  public boolean createQuest(QuestCreateDto dto) {
    if(Objects.isNull(dto.getStatus())) dto.setStatus(Status.ACTIVE);
    Quest quest = modelMapper.toQuest(dto);
    try {
      self.save(quest);
    } catch (Exception e){
      log.error("==============>createQuest:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public boolean updateQuest(Long id, QuestUpdateDto dto) {
    Quest quest = questStorage.findById(id);
    if (quest == null) {
      throw new BadRequestException(Constants.QUEST_NOT_FOUND);
    }
    modelMapper.mapQuestUpdateDtoToQuest(dto, quest);
    try {
      self.save(quest);
    } catch (Exception e){
      log.error("==============>updateQuest:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public boolean deleteQuest(Long id) {
    Quest quest = questStorage.findById(id);
    if (quest == null) {
      throw new BadRequestException(Constants.QUEST_NOT_FOUND);
    }
    quest.setStatus(Status.DELETE);
    try {
      self.save(quest);
    } catch (Exception e){
      log.error("==============>deleteQuest:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public void save(Quest quest){
    questStorage.save(quest);
  }
}
