package com.wiinvent.gami.domain.repositories.game;

import com.wiinvent.gami.domain.entities.game.GamePackage;
import com.wiinvent.gami.domain.entities.type.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GamePackageRepository extends JpaRepository<GamePackage, Integer>, JpaSpecificationExecutor<GamePackage> {
  GamePackage findByIdAndStatusIn(int id, List<Status> statuses);
}