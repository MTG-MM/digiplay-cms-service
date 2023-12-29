package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.IndustryGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryGroupRepository extends JpaRepository<IndustryGroupEntity, Integer> {
}
