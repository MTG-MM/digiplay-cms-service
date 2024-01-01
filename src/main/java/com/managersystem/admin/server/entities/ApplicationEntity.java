package com.managersystem.admin.server.entities;

import com.managersystem.admin.server.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Table(schema = "prod", name = "application")
@Data
@Entity
public class ApplicationEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "application_code")
  private String applicationCode;

  @Column(name = "application_name")
  private String applicationName;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "nav_link_url")
  private String navLinkUrl;

  @Column(name = "description")
  private String description;

  @Column(name = "total_seller")
  private Long totalSeller = 0L;

  @Column(name = "total_purchase")
  private Long totalPurchase = 0L;

  @Column(name = "total_view")
  private Long totalView = 0L;
}
