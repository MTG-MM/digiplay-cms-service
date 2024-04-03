package com.wiinvent.gami.domain.entities.game;

import com.wiinvent.gami.domain.entities.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "game")
public class Game extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @NotNull
  @Column(name = "category_id", nullable = false)
  private Integer categoryId;

  @NotNull
  @Column(name = "team_id", nullable = false)
  private UUID teamId;

  @Size(max = 500)
  @Column(name = "name", length = 500)
  private String name;

  @Size(max = 500)
  @Column(name = "image_url", length = 500)
  private String imageUrl;

  @Size(max = 500)
  @Column(name = "secret_key", length = 500)
  private String secretKey;

  @Size(max = 500)
  @Column(name = "thumb_url", length = 500)
  private String thumbUrl;

  @Size(max = 2000)
  @Column(name = "banner", length = 2000)
  private String banner;

  @Size(max = 2000)
  @Column(name = "description", length = 2000)
  private String description;

  @Column(name = "priority")
  private Integer priority;

  @Size(max = 500)
  @Column(name = "nav_link", length = 500)
  private String navLink;

  @Size(max = 500)
  @NotNull
  @Column(name = "api_verify_account", nullable = false, length = 500)
  private String apiVerifyAccount;

  @Size(max = 1000)
  @Column(name = "body_api_verify_account", length = 1000)
  private String bodyApiVerifyAccount;

  @Size(max = 1000)
  @NotNull
  @Column(name = "api_payment", nullable = false, length = 1000)
  private String apiPayment;

  @Size(max = 1000)
  @Column(name = "body_api_payment", length = 1000)
  private String bodyApiPayment;

  @Column(name = "game_type_id")
  private Integer gameTypeId;

}