package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.ChallengeCreateDto;
import com.wiinvent.gami.domain.dto.ChallengeUpdateDto;
import com.wiinvent.gami.domain.entities.Challenge;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.response.ChallengeResponse;
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

@Service
@Log4j2
public class ChallengeService extends BaseService{

  @Autowired
  @Lazy
  private ChallengeService self;

  public ChallengeResponse getChallengeDetails(Integer id){
    Challenge challenge = challengeStorage.findChallengeById(id);
    if (challenge == null){
      throw new BadRequestException(Constants.CHALLENGE_NOT_FOUND);
    }
    return modelMapper.toChallengeResponse(challenge);
  }

  public Page<ChallengeResponse> findAll(Integer gameId, Status status, Pageable pageable){
    Page<Challenge> challenges = challengeStorage.findAll(gameId, status, pageable);
    return modelMapper.toPageChallengeResponse(challenges);
  }

  public boolean createChallenge(ChallengeCreateDto dto) {
    Challenge challenge = modelMapper.toChallenge(dto);
    try {
      self.save(challenge);
    } catch (Exception e){
      log.error("==============>createChallenge:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public boolean updateChallenge(Integer id, ChallengeUpdateDto dto) {
    Challenge challenge = challengeStorage.findChallengeById(id);
    if (challenge == null) {
      throw new BadRequestException(Constants.CHALLENGE_NOT_FOUND);
    }
    modelMapper.mapChallengeUpdateDtoToChallenge(dto, challenge);

    try {
      self.save(challenge);
    } catch (Exception e){
      log.error("==============>updateChallenge:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public boolean deleteChallenge(Integer id) {
    Challenge challenge = challengeStorage.findChallengeById(id);
    if (challenge == null) {
      throw new BadRequestException(Constants.CHALLENGE_NOT_FOUND);
    }
    challenge.setStatus(Status.DELETE);
    try {
      self.save(challenge);
    } catch (Exception e){
      log.error("==============>deleteAchievement:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }
  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public void save(Challenge challenge){
    challengeStorage.save(challenge);
  }
}
