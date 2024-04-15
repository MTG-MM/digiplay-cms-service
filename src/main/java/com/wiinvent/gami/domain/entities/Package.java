package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "package")
public class Package extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Size(max = 50)
  @NotNull
  @Column(name = "code", nullable = false, length = 50)
  private String code;

  @NotNull
  @Column(name = "point", nullable = false)
  private Integer point;

  @NotNull
  @Column(name = "coin", nullable = false)
  private Integer coin;

  @Column(name = "day_duration")
  private Integer daySub;

  @Lob
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;

  @ColumnDefault("0")
  @Column(name = "coin_daily")
  private Integer coinDaily;

  @Size(max = 500)
  @Column(name = "external_image_id", length = 500)
  private String externalImageId;

  @NotNull
  @Column(name = "package_type_id", nullable = false)
  private Integer packageTypeId;

  @ColumnDefault("0")
  @Column(name = "point_daily")
  private Integer pointDaily;

  @Size(max = 500)
  @Column(name = "payment_method_info", length = 500)
  private String paymentMethodInfo;

  @Column(name = "start_time")
  private Long startTime;

  @Column(name = "end_time")
  private Long endTime;

  @ColumnDefault("0")
  @Column(name = "point_bonus")
  private Integer pointBonus;

  @ColumnDefault("0")
  @Column(name = "coin_bonus")
  private Integer coinBonus;

  public static List<Status> getListStatusShow(){
    return List.of(Status.ACTIVE, Status.INACTIVE);
  }
}