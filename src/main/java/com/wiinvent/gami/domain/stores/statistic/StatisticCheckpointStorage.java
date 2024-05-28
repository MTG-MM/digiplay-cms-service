package com.wiinvent.gami.domain.stores.statistic;

import com.wiinvent.gami.domain.entities.statistic.StatisticCheckpoint;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.stereotype.Component;

@Component
public class StatisticCheckpointStorage extends BaseStorage {

  public StatisticCheckpoint findById(String id) {
    return statisticCheckpointRepository.findById(id).orElse(null);
  }

  public void save(StatisticCheckpoint statisticCheckpoint) {
    statisticCheckpointRepository.save(statisticCheckpoint);
  }
}
