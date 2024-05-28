package com.wiinvent.gami.domain.repositories.statistic;

import com.wiinvent.gami.domain.entities.statistic.StatisticUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface StatisticUserRepository extends JpaRepository<StatisticUser, Integer> {
  StatisticUser findByDate(LocalDate date);
}
