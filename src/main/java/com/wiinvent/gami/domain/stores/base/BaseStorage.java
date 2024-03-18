package com.wiinvent.gami.domain.stores.base;

import com.wiinvent.gami.domain.utils.CacheKey;
import com.wiinvent.gami.domain.utils.RemoteCache;
import com.wiinvent.gami.domain.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseStorage {

  @Autowired protected RemoteCache remoteCache;
  @Autowired protected CacheKey cacheKey;
  @Autowired protected AccountRepository accountRepository;
  @Autowired protected UserRepository userRepository;
  @Autowired protected ApplicationRepository applicationRepository;
  @Autowired protected RewardItemHistoryRepository rewardItemHistoryRepository;
  @Autowired protected RewardItemRepository rewardItemRepository;
  @Autowired protected RewardScheduleRepository rewardScheduleRepository;
  @Autowired protected RewardSegmentDetailRepository rewardSegmentDetailRepository;
  @Autowired protected RewardSegmentRepository rewardSegmentRepository;
  @Autowired protected RewardStateRepository rewardStateRepository;
  @Autowired protected RewardStateLogRepository rewardStateLogRepository;
  @Autowired protected RewardTypeRepository rewardTypeRepository;
  @Autowired protected RewardItemStoreRepository rewardItemStoreRepository;
  @Autowired protected VoucherDetailRepository voucherDetailRepository;
  @Autowired protected ProductDetailRepository productDetailRepository;
  @Autowired protected UserSegmentRepository userSegmentRepository;
  @Autowired protected RewardItemStatisticRepository rewardItemStatisticRepository;
}
