package com.wiinvent.gami.domain.entities.leaderboard;


import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "leaderboard")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Leaderboard extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Size(max = 100)
  @NotNull
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Size(max = 100)
  @NotNull
  @Column(name = "code", nullable = false, length = 100)
  private String code;

  @Size(max = 100)
  @Column(name = "status", length = 100)
  private Status status;

  @Column(name = "reward_items")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<UserRewardItems> rewardItems;

}