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
@Table(name = "game_package")
public class GamePackage extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @NotNull
  @Column(name = "game_id", nullable = false)
  private Integer gameId;

  @Size(max = 50)
  @NotNull
  @Column(name = "code", nullable = false, length = 50)
  private String code;

  @Column(name = "price")
  private Integer price;

  @NotNull
  @Column(name = "point", nullable = false)
  private Integer point;

  @NotNull
  @Column(name = "coin", nullable = false)
  private Integer coin;

  @Size(max = 1000)
  @Column(name = "image_url", length = 1000)
  private String imageUrl;

  @Size(max = 1000)
  @Column(name = "thumb_url", length = 1000)
  private String thumbUrl;

  @Size(max = 1000)
  @Column(name = "description", length = 1000)
  private String description;

  @Lob
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;

  @Column(name = "priority")
  private Integer priority;

}