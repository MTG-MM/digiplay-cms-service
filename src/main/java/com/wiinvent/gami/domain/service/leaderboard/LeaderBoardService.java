package com.wiinvent.gami.domain.service.leaderboard;

import com.wiinvent.gami.domain.dto.ExchangeItemStoreCreateDto;
import com.wiinvent.gami.domain.dto.ExchangeItemStoreUpdateDto;
import com.wiinvent.gami.domain.dto.LeaderboardCreateDto;
import com.wiinvent.gami.domain.dto.LeaderboardUpdateDto;
import com.wiinvent.gami.domain.entities.ExchangeItemStore;
import com.wiinvent.gami.domain.entities.leaderboard.Leaderboard;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.type.StoreType;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.ExchangeItemStoreResponse;
import com.wiinvent.gami.domain.response.LeaderboardResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.stores.leaderboard.LeaderboardStorage;
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
public class LeaderBoardService extends BaseService {
  @Autowired
  @Lazy
  private LeaderBoardService self;
  public LeaderboardResponse getLeaderboardDetail(Long id) {
    Leaderboard leaderboard = leaderboardStorage.findById(id);
    if (leaderboard == null) {
      throw new ResourceNotFoundException("Leaderboard not found");
    }
    return modelMapper.toLeaderboardResponse(leaderboard);
  }

  public PageResponse<LeaderboardResponse> getAllLeaderboard(Long id, String name, Pageable pageable) {
    Page<Leaderboard> leaderboards = leaderboardStorage.findAll(id, name, pageable);
    Page<LeaderboardResponse> responses = modelMapper.toPageLeaderboardResponseResponse(leaderboards);
    return PageResponse.createFrom(responses);
  }

  public Boolean createLeaderboard(LeaderboardCreateDto createDto) {
    Leaderboard leaderboard = modelMapper.toLeaderboard(createDto);
    try {
      self.save(leaderboard);
    } catch (Exception e) {
      log.debug("==============>createLeaderboard:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public Boolean updateLeaderboard(Long id, LeaderboardUpdateDto updateDto) {
    Leaderboard leaderboard = leaderboardStorage.findById(id);
    if (leaderboard == null) {
      throw new ResourceNotFoundException("Leaderboard not found");
    }

    modelMapper.mapLeaderboardUpdateDtoToLeaderboard(updateDto, leaderboard);
    try {
      self.save(leaderboard);
    } catch (Exception e) {
      log.debug("==============>updateLeaderboard:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public boolean deleteLeaderboard(Long id) {
    Leaderboard leaderboard = leaderboardStorage.findById(id);
    if (leaderboard == null) {
      throw new ResourceNotFoundException("Leaderboard not found");
    }

    leaderboard.setStatus(Status.DELETE);
    try {
      self.save(leaderboard);
    } catch (Exception e) {
      log.error("==============>deleteLeaderboard:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public void save(Leaderboard leaderboard) {
    leaderboardStorage.save(leaderboard);
  }
}
