package com.wiinvent.gami.domain.repositories.game;

import com.wiinvent.gami.domain.entities.game.GameCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCategoryRepository extends JpaRepository<GameCategory, Integer> {
}