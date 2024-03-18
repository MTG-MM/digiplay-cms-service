package com.wiinvent.gami.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "reward_state")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RewardState {

  @Id
  private Long id; // map 1 - 1 voi reward schedule

  @Column(name = "last_minute")
  private Integer lastMinute;

  @Column(name = "count_minute")
  private Long countMinute;

  @Column(name = "last_hour")
  private Integer lastHour;

  @Column(name = "count_hour")
  private Long countHour;

  @Column(name = "last_day")
  private Integer lastDay;

  @Column(name = "count_day")
  private Long countDay;

  public void addCountDay(long amount) {
    countDay += amount;
  }

  public void addCountHour(long amount) {
    countHour += amount;
  }

  public void addCountMinute(long amount) {
    countMinute += amount;
  }
}
