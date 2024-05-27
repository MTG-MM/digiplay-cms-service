package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.ChallengeDetailCreateDto;
import com.wiinvent.gami.domain.dto.ChallengeDetailUpdateDto;
import com.wiinvent.gami.domain.entities.ChallengeDetail;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.response.ChallengeDetailResponse;
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
public class ChallengeDetailService extends BaseService{

  @Autowired
  @Lazy
  private ChallengeDetailService self;

  public Page<ChallengeDetailResponse> findAll(Integer challengeId, Integer level, Status status, Pageable pageable) {
    Page<ChallengeDetail> challengeDetails = challengeDetailStorage.findAll(challengeId, level, status, pageable);
    return modelMapper.toPageChallengeDetailResponse(challengeDetails);
  }

  public ChallengeDetailResponse getChallengeDetail(Integer id) {
    ChallengeDetail challengeDetail = challengeDetailStorage.findChallengeDetailById(id);
    if (challengeDetail == null) {
      throw new BadRequestException(Constants.CHALLENGE_NOT_FOUND);
    }
    return modelMapper.toChallengeDetailResponse(challengeDetail);
  }

  public boolean createChallengeDetail(ChallengeDetailCreateDto dto) {
    ChallengeDetail challengeDetail = modelMapper.toChallengeDetail(dto);
    try {
      self.save(challengeDetail);
    } catch (Exception e){
      log.error("==============>createChallengeDetail:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public boolean updateChallengeDetail(Integer id, ChallengeDetailUpdateDto dto) {
    ChallengeDetail challengeDetail = challengeDetailStorage.findChallengeDetailById(id);
    if (challengeDetail == null) {
      throw new BadRequestException(Constants.CHALLENGE_NOT_FOUND);
    }
    modelMapper.mapChallengeDetailUpdateDtoToChallengeDetail(dto, challengeDetail);

    try {
      self.save(challengeDetail);
    } catch (Exception e){
      log.error("==============>updateChallengeDetail:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public boolean deleteChallengeDetail(Integer id) {
    ChallengeDetail challengeDetail = challengeDetailStorage.findChallengeDetailById(id);
    if (challengeDetail == null) {
      throw new BadRequestException(Constants.CHALLENGE_NOT_FOUND);
    }
    challengeDetail.setStatus(Status.DELETE);
    try {
      self.save(challengeDetail);
    } catch (Exception e){
      log.error("==============>deleteChallengeDetail:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public void save(ChallengeDetail challengeDetail){
    challengeDetailStorage.save(challengeDetail);
  }
}
