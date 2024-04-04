package com.wiinvent.gami.domain.entities.game;

import com.wiinvent.gami.domain.dto.GameCategoryUpdateDto;
import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "game_category")
public class GameCategory extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Size(max = 500)
  @Column(name = "name", length = 500)
  private String name;

  @Lob
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;

  @Lob
  @Column(name = "category_type")
  private String categoryType;

  @Column(name = "is_require_sub")
  private Boolean isRequireSub;

  @Column(name = "point")
  private Integer point;

  @Column(name = "coin")
  private Integer coin;

  public void from(GameCategoryUpdateDto dto){
    this.name = dto.getName();
    this.status = dto.getStatus();
    this.categoryType = dto.getCategoryType();
    this.isRequireSub = dto.getIsRequireSub();
    this.coin = dto.getCoin();
  }

}