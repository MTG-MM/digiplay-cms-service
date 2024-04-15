package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.type.BannerType;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "banner")
public class Banner extends BaseEntity{
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private BannerType type;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;

  @Column(name = "link_to")
  private String linkTo;

  @Column(name = "link_tracking")
  private String linkTracking;

  public static List<Status> getListStatusShow(){
    return List.of(Status.ACTIVE, Status.INACTIVE);
  }
}