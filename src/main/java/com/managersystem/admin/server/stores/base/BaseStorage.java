package com.managersystem.admin.server.stores.base;

import com.managersystem.admin.server.entities.RewardStateLog;
import com.managersystem.admin.server.repositories.*;
import com.managersystem.admin.server.utils.CacheKey;
import com.managersystem.admin.server.utils.RemoteCache;
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
  @Autowired protected VoucherStoreRepository voucherStoreRepository;
  @Autowired protected VoucherDetailRepository voucherDetailRepository;
}
