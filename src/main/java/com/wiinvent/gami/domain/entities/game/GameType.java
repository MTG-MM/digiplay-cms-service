package com.wiinvent.gami.domain.entities.game;

import com.wiinvent.gami.domain.dto.GameTypeUpdateDto;
import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.GameTypeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "game_type")
public class GameType extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private GameTypeStatus status;

  public void from(GameTypeUpdateDto dto){
    this.name = dto.getName();
    this.description = dto.getDescription();
    this.status = dto.getStatus();
  }
}