package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.GameStar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameStarRepository extends JpaRepository<GameStar, Integer> {
  List<GameStar> findByGameIdIn(List<Integer> gameIds);
}