package com.wiinvent.gami.domain.service.leaderboard;

import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Helper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class LeaderBoardHistoryService extends BaseService {

  private final LeaderBoardEventService leaderBoardEventService;
  private final LeaderBoardHistoryService self;
  private final LeaderBoardRewardService leaderBoardRewardService;

  @Lazy
  public LeaderBoardHistoryService(LeaderBoardEventService leaderBoardEventService, LeaderBoardHistoryService self, LeaderBoardRewardService leaderBoardRewardService) {
    super();
    this.leaderBoardEventService = leaderBoardEventService;
    this.self = self;
    this.leaderBoardRewardService = leaderBoardRewardService;
  }


}

