package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.response.RewardResponse;
import com.wiinvent.gami.domain.entities.type.PeriodLimitType;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.service.base.BaseService;
import com.wiinvent.gami.domain.utils.Constant;
import com.wiinvent.gami.domain.entities.RewardSegment;
import com.wiinvent.gami.domain.entities.RewardSegmentDetail;
import com.wiinvent.gami.domain.entities.User;
import com.wiinvent.gami.domain.entities.UserSegment;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Log4j2
public class RewardPoolService extends BaseService {

  @Autowired @Lazy RewardPoolService rewardPoolService;
  @Autowired @Lazy RewardItemService rewardItemService;
  @Autowired @Lazy UserSegmentService userSegmentService;


}
