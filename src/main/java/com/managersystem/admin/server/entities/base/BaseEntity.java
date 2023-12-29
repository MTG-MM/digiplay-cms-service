package com.managersystem.admin.server.entities.base;

import jakarta.persistence.Column;

import java.util.UUID;


public class BaseEntity {

  @Column(name = "created_at")
  protected Long createdAt;

  @Column(name = "updated_at")
  protected Long updatedAt;

  @Column(name = "updated_by")
  protected UUID updatedBy;

  @Column(name = "created_by")
  protected UUID createdBy;
}
