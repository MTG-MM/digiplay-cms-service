package com.managersystem.admin.server.service.base;

import com.managersystem.admin.ModelMapper;
import com.managersystem.admin.server.stores.*;
import com.managersystem.admin.server.utils.CacheKey;
import com.managersystem.admin.server.utils.LockManager;
import com.managersystem.admin.server.utils.RemoteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseService {
  @Autowired protected ModelMapper modelMapper;
  @Autowired protected RemoteCache remoteCache;
  @Autowired protected CacheKey cacheKey;
  @Autowired protected LockManager lockManager;
  @Autowired protected AccountStorage accountStorage;
  @Autowired protected UserStorage userStorage;
  @Autowired protected UserSegmentStorage userSegmentStorage;
  @Autowired protected ApplicationStorage applicationStorage;
  @Autowired protected RewardItemHistoryStorage rewardItemHistoryStorage;
  @Autowired protected RewardItemStorage rewardItemStorage;
  @Autowired protected RewardScheduleStorage rewardScheduleStorage;
  @Autowired protected RewardSegmentDetailStorage rewardSegmentDetailStorage;
  @Autowired protected RewardSegmentStorage rewardSegmentStorage;
  @Autowired protected RewardStateStorage rewardStateStorage;
  @Autowired protected RewardStateLogStorage rewardStateLogStorage;
  @Autowired protected RewardTypeStorage rewardTypeStorage;
  @Autowired protected VoucherDetailStorage voucherDetailStorage;
  @Autowired protected ProductDetailStorage productDetailStorage;
  @Autowired protected RewardItemStoreStorage rewardItemStoreStorage;
}
