package com.wiinvent.gami.domain.entities.statistic;

import com.wiinvent.gami.domain.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "statistic_checkpoint")
public class StatisticCheckpoint {
  @Id
  @Column(name = "id", nullable = false, length = 255)
  private String id;

  @Column(name = "check_point", nullable = false, length = 50)
  private LocalDate checkPoint;
}
