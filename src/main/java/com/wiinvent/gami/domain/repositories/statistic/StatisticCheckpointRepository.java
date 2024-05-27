package com.wiinvent.gami.domain.repositories.statistic;

import com.wiinvent.gami.domain.entities.statistic.StatisticCheckpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticCheckpointRepository extends JpaRepository<StatisticCheckpoint, String> {
}
