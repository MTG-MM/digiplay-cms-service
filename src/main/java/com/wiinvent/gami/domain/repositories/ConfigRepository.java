package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Integer> {
}