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

  @Size(max = 500)
  @Column(name = "image_url", length = 500)
  private String imageUrl;

  @Column(name = "price")
  private Integer price;

  @NotNull
  @Column(name = "point", nullable = false)
  private Integer point;

  @NotNull
  @Column(name = "coin", nullable = false)
  private Integer coin;


  @Size(max = 500)
  @Column(name = "thumb_url", length = 500)
  private String thumbUrl;

  @Column(name = "day_sub")
  private Integer daySub;

  @Lob
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;

  @Lob
  @Column(name = "package_type")
  private String packageType;

}