package com.wiinvent.gami.domain.service.reward;

import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.service.user.UserSegmentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RewardPoolService extends BaseService {

  @Autowired @Lazy RewardPoolService rewardPoolService;
  @Autowired @Lazy RewardItemService rewardItemService;
  @Autowired @Lazy
  UserSegmentService userSegmentService;


}
