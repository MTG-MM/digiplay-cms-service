package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Integer> {
  List<ApplicationEntity> findByApplicationCodeContaining(String code);
}
