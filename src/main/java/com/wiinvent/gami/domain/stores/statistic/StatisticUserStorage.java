package com.wiinvent.gami.domain.stores.statistic;

import com.wiinvent.gami.domain.entities.statistic.StatisticUser;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class StatisticUserStorage extends BaseStorage {

  public StatisticUser findByDate(LocalDate date) {
    return statisticUserRepository.findByDate(date);
  }

  public void save(StatisticUser statisticUser) {
    statisticUserRepository.save(statisticUser);
  }
}
