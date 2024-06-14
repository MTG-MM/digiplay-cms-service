package com.wiinvent.gami.domain.service.leaderboard;
import com.wiinvent.gami.domain.service.BaseService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LeaderBoardRewardService extends BaseService {
  private final LeaderBoardEventService leaderBoardEventService;
  private final LeaderBoardHistoryService leaderBoardHistoryService;

  @Lazy
  public LeaderBoardRewardService(LeaderBoardEventService leaderBoardEventService,  LeaderBoardHistoryService leaderBoardHistoryService) {
    super();
    this.leaderBoardEventService = leaderBoardEventService;
    this.leaderBoardHistoryService = leaderBoardHistoryService;
  }


}
