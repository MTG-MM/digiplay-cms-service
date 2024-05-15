package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.type.DailyTaskType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "task")
@Data
public class Task extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Size(max = 100)
  @Column(name = "name", length = 100)
  private String name;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private DailyTaskType type;

  @Size(max = 1000)
  @Column(name = "description", length = 1000)
  private String description;

  @Column(name = "limit_day")
  private Integer limitDay;

  @Column(name = "score")
  private Integer score;

  @Lob
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;

  @Column(name = "reward_items")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<UserRewardItems> rewardItems;
}
