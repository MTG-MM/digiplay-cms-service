package com.wiinvent.gami.domain.repositories.game;

import com.wiinvent.gami.domain.entities.game.GamePackage;
import com.wiinvent.gami.domain.entities.type.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePackageRepository extends JpaRepository<GamePackage, Integer> {
  GamePackage findByIdAndStatusNot(int id, Status status);
}