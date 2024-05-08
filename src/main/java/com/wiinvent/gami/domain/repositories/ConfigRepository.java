package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.Config;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Integer> {
  Config findByKey(String key);

  Page<Config> findAll(Pageable pageable);

  Boolean existsByKey(String key);}