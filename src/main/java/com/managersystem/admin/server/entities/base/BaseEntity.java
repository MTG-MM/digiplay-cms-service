package com.managersystem.admin.server.entities.base;

import com.managersystem.admin.server.utils.DateUtils;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

  @Column(name = "created_at")
  private Long createdAt = DateUtils.getNowMillisAtUtc();

  @Column(name = "updated_at")
  private Long updatedAt = DateUtils.getNowMillisAtUtc();

//  @Column(name = "updated_by")
//  public UUID updatedBy;
//
//  @Column(name = "created_by")
//  public UUID createdBy;
//
//  @Column(name = "string")
//  public String string;

}
