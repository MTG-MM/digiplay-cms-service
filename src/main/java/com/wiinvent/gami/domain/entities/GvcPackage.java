package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.base.BaseEntity;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "gvc_package")
public class GvcPackage extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @NotNull
  @Column(name = "game_id", nullable = false)
  private Integer gameId;

  @Size(max = 50)
  @Column(name = "code", length = 50)
  private String code;

  @Column(name = "gvc1_amount")
  private Integer gvc1Amount;

  @Column(name = "gvc2_amount")
  private Integer gvc2Amount;

  @Lob
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;

}