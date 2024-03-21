package com.wiinvent.gami.domain.entities.gvc;

import com.wiinvent.gami.domain.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "gcv_history")
public class GcvHistory extends BaseEntity {
  @Id
  @Size(max = 16)
  @Column(name = "id", nullable = false, length = 16)
  private String id;

  @Size(max = 16)
  @NotNull
  @Column(name = "user_id", nullable = false, length = 16)
  private UUID userId;

  @Column(name = "gvc1_amount")
  private Integer gvc1Amount;

  @Column(name = "gvc1_balance")
  private Integer gvc1Balance;

  @Column(name = "gvc2_amount")
  private Integer gvc2Amount;

  @Column(name = "gvc2_balance")
  private Integer gvc2Balance;

  @Size(max = 50)
  @Column(name = "gvc_package_code", length = 50)
  private String gvcPackageCode;

  @Size(max = 500)
  @Column(name = "note", length = 500)
  private String note;

}