package com.managersystem.admin.server.entities;

import com.managersystem.admin.server.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Table(schema = "prod", name = "industry_group")
@Data
@Entity
public class IndustryGroupEntity  extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "industry_group_code")
  private String industryGroupCode;

  @Column(name = "industry_group_name")
  private String industryGroupName;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "nav_link_url")
  private String navLinkUrl;

  @Column(name = "description")
  private String description;

  @Column(name = "total_seller")
  private Long totalSeller;

  @Column(name = "total_purchase")
  private Long totalPurchase;

  @Column(name = "total_view")
  private Long totalView;
}
