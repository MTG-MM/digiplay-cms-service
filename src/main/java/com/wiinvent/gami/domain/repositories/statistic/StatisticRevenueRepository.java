package com.wiinvent.gami.domain.repositories.statistic;

import com.wiinvent.gami.domain.entities.statistic.StatisticRevenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface StatisticRevenueRepository extends JpaRepository<StatisticRevenue, Integer> {
  StatisticRevenue findByDate(LocalDate date);
}
