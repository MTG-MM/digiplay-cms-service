package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
  List<Application> findByCodeContaining(String code, Pageable pageable);



  @Query("SELECT a FROM Application a ORDER BY a.updatedAt DESC")
  Page<Application> findAllWithSorting(Pageable pageable);

}
