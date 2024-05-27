package com.wiinvent.gami.domain.stores.statistic;

import com.wiinvent.gami.domain.entities.statistic.StatisticRevenue;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class StatisticRevenueStorage extends BaseStorage {
  public StatisticRevenue findByDate(LocalDate date) {
    return statisticRevenueRepository.findByDate(date);
  }

  public void save(StatisticRevenue statisticRevenue) {
    statisticRevenueRepository.save(statisticRevenue);
  }
}
