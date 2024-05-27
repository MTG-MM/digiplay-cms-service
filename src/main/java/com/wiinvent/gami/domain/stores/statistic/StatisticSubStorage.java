package com.wiinvent.gami.domain.stores.statistic;

import com.wiinvent.gami.domain.entities.statistic.StatisticSub;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class StatisticSubStorage extends BaseStorage {
  public StatisticSub findByDate(LocalDate date) {
    return statisticSubRepository.findByDate(date);
  }

  public void save(StatisticSub statisticSub) {
    statisticSubRepository.save(statisticSub);
  }
}
