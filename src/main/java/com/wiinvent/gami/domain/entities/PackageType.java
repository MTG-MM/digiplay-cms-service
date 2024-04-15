package com.wiinvent.gami.domain.entities;


import com.wiinvent.gami.domain.entities.converter.UserRewardItemConverter;
import com.wiinvent.gami.domain.entities.type.ProductPackageType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "package_type")
public class PackageType extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Size(max = 100)
  @Column(name = "name", length = 100)
  private String name;

  @Lob
  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private ProductPackageType type;

  @ColumnDefault("b'0'")
  @Column(name = "is_skip_ads")
  private Boolean isSkipAds;

  @ColumnDefault("0")
  @Column(name = "decrease_quest_cd_percent")
  private Integer decreaseQuestCdPercent;

  @ColumnDefault("0")
  @Column(name = "point_bonus_rate")
  private Integer bonusRate;

  @Column(name = "reward_items")
  @Convert(converter = UserRewardItemConverter.class)
  private List<UserRewardItems> rewardItems;

  @ColumnDefault("0")
  @Column(name = "decrease_point_percent")
  private Integer decreasePointPercent;

  @ColumnDefault("0")
  @Column(name = "exp_bonus")
  private Integer expBonus;

  @ColumnDefault("0")
  @Column(name = "free_spin_day")
  private Integer freeSpinDay;

  @ColumnDefault("INACTIVE")
  @Lob
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;

  public static List<Status> getListStatusShow(){
    return List.of(Status.ACTIVE, Status.INACTIVE);
  }

}