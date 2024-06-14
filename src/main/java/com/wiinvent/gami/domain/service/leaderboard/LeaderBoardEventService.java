package com.wiinvent.gami.domain.service.leaderboard;

import com.wiinvent.gami.domain.entities.leaderboard.LeaderboardEvent;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constants;
import com.wiinvent.gami.domain.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LeaderBoardEventService extends BaseService {
  private final LeaderBoardRewardService leaderBoardRewardService;

  public LeaderBoardEventService(LeaderBoardRewardService leaderBoardRewardService) {
    super();
    this.leaderBoardRewardService = leaderBoardRewardService;
  }


}
