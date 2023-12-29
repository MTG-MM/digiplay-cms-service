package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.IndustryGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndustryGroupRepository extends JpaRepository<IndustryGroupEntity, Integer> {
  List<IndustryGroupEntity> findByIndustryGroupNameContaining(String name);
}
