package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.ApplicationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Integer> {
  List<ApplicationEntity> findByApplicationCodeContaining(String code, Pageable pageable);



  @Query("SELECT a FROM ApplicationEntity a ORDER BY a.updatedAt DESC")
  Page<ApplicationEntity> findAllWithSorting(Pageable pageable);

}
