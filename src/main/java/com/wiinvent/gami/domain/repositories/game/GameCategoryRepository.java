package com.wiinvent.gami.domain.repositories.game;

import com.wiinvent.gami.domain.entities.game.GameCategory;
import com.wiinvent.gami.domain.entities.type.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameCategoryRepository extends JpaRepository<GameCategory, Integer> {
  GameCategory findGameCategoryByIdAndStatusIn(Integer id, List<Status> statuses);
  Page<GameCategory> findAllByStatusIn(List<Status> statuses, Pageable pageable);
  List<GameCategory> findGameCategoryByStatusIn(List<Status> statuses);
  List<GameCategory> findGameCategoriesByIdIn(List<Integer> ids);
}