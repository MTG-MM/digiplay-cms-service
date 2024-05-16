package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.type.CharacterCategoryType;
import com.wiinvent.gami.domain.entities.type.CharacterGenderType;
import com.wiinvent.gami.domain.entities.type.CharacterType;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "characters")
public class Character extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Size(max = 100)
  @NotNull
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "category_type")
  @Enumerated(EnumType.STRING)
  private CharacterCategoryType categoryType;

  @Column(name = "gender")
  @Enumerated(EnumType.STRING)
  private CharacterGenderType gender;

  @Lob
  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private CharacterType type;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;

  @Column(name = "coin_price")
  private Integer coinPrice = 0;

  @Column(name = "point_price")
  private Integer pointPrice = 0;

  @Size(max = 50)
  @Column(name = "external_id", length = 50)
  private String externalId;

  @Column(name = "is_default")
  private Boolean isDefault;

  @Column(name = "is_sell")
  private Boolean isSell;

  @Column(name = "is_hot")
  private Boolean isHot;

  @Column(name = "is_trend")
  private Boolean isTrend;

  @Column(name = "is_new")
  private Boolean isNew;

  @Column(name = "is_event")
  private Boolean isEvent;
  @Column(name = "is_expire")
  private Boolean isExpire;

  @Column(name = "minute_duration")
  private Long duration;
}