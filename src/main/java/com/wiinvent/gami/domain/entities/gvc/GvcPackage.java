package com.wiinvent.gami.domain.entities.gvc;

import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

  @Column(name = "point")
  private Integer point;

  @Column(name = "coin")
  private Integer coin;

  @Lob
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;

  public static List<Status> getListStatusShow(){
    return List.of(Status.ACTIVE, Status.INACTIVE);
  }

  public static List<Status> getListStatusNotShow(){
    return List.of(Status.DELETE);
  }
}