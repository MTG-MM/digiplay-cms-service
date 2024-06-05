package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Data
@Entity
@Table(name = "challenge_detail")
public class ChallengeDetail extends BaseEntity {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  @Column(name = "challenge_id", nullable = false)
  private Integer challengeId;

  @Size(max = 100)
  @Column(name = "name", length = 100)
  private String name;

  @Size(max = 50)
  @Column(name = "code", length = 50)
  private String code;

  @Column(name = "point_price")
  private Integer pointPrice = 0;

  @Column(name = "coin_price")
  private Integer coinPrice = 0;

  @Column(name = "level")
  private Integer level = 0;

  @Lob
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;

  @Column(name = "reward_items")
  @JdbcTypeCode(SqlTypes.JSON)
//  @Convert(converter = UserRewardItemConverter.class)
  private List<UserRewardItems> rewardItems;

  @ColumnDefault("b'0'")
  @Column(name = "is_special")
  private Boolean isSpecial;

  @ColumnDefault("0")
  @Column(name = "duration")
  private Integer duration;

  @Column(name = "reward_item_special")
  @JdbcTypeCode(SqlTypes.JSON)
//  @Convert(converter = UserRewardItemConverter.class)
  private List<UserRewardItems> rewardItemSpecial;
}
