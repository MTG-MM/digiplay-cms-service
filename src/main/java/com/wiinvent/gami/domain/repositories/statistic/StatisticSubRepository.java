package com.wiinvent.gami.domain.repositories.statistic;

import com.wiinvent.gami.domain.entities.statistic.StatisticSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface StatisticSubRepository extends JpaRepository<StatisticSub, Integer> {
  StatisticSub findByDate(LocalDate date);
}
