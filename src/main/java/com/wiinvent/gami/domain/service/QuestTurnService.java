package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.QuestTurnCreateDto;
import com.wiinvent.gami.domain.dto.QuestTurnUpdateDto;
import com.wiinvent.gami.domain.entities.QuestTurn;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.response.QuestTurnResponse;
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
public class QuestTurnService extends BaseService{

  @Autowired
  @Lazy
  QuestTurnService self;

  public Page<QuestTurnResponse> findAll(String name, Integer questNumber, Pageable pageable){
    Page<QuestTurn> questTurns = questTurnStorage.findAll(name, questNumber, pageable);
    return modelMapper.toPageQuestTurnResponse(questTurns);
  }

  public QuestTurnResponse getQuestTurnDetail(Long id){
    QuestTurn questTurn = questTurnStorage.findById(id);
    if (questTurn == null) {
      throw new BadRequestException(Constants.QUEST_NOT_FOUND);
    }
    return modelMapper.toQuestTurnResponse(questTurn);
  }

  public boolean createQuestTurn(QuestTurnCreateDto dto) {
    QuestTurn questTurn = modelMapper.toQuestTurn(dto);
    try {
      self.save(questTurn);
    } catch (Exception e){
      log.error("==============>createQuestTurn:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public boolean updateQuestTurn(Long id, QuestTurnUpdateDto dto) {
    QuestTurn questTurn = questTurnStorage.findById(id);
    if (questTurn == null) {
      throw new BadRequestException(Constants.QUEST_NOT_FOUND);
    }
    modelMapper.mapQuestTurnUpdateDtoToQuestTurn(dto, questTurn);
    try {
      self.save(questTurn);
    } catch (Exception e){
      log.error("==============>updateQuestTurn:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public boolean deleteQuestTurn(Long id) {
    QuestTurn questTurn = questTurnStorage.findById(id);
    if (questTurn == null) {
      throw new BadRequestException(Constants.QUEST_NOT_FOUND);
    }
    questTurn.setStatus(Status.DELETE);
    try {
      self.save(questTurn);
    } catch (Exception e){
      log.error("==============>deleteQuestTurn:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public void save(QuestTurn questTurn){
    questTurnStorage.save(questTurn);
  }
}
