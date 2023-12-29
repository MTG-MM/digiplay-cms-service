package com.managersystem.admin.server.entities.base;

import com.managersystem.admin.server.utils.DateUtils;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@NoArgsConstructor
public class BaseEntity {

  @Column(name = "created_at")
  public Long createdAt = DateUtils.getNowMillisAtUtc();

  @Column(name = "updated_at")
  public Long updatedAt = DateUtils.getNowMillisAtUtc();

  @Column(name = "updated_by")
  public UUID updatedBy;

  @Column(name = "created_by")
  public UUID createdBy;


  @PrePersist
  protected void onCreate() {
    updatedAt = createdAt = DateUtils.getNowMillisAtUtc();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = DateUtils.getNowMillisAtUtc();
  }
}
