package com.wiinvent.gami.domain.stores.game;

import com.wiinvent.gami.domain.entities.game.GameCategory;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GameCategoryStorage extends BaseStorage {
  public GameCategory save(GameCategory gameCategory){
    return gameCategoryRepository.save(gameCategory);
  }

  public GameCategory findById(Integer id){
    return gameCategoryRepository.findGameCategoryByIdAndStatusIn(id, GameCategory.getListStatusShow());
  }

  public Page<GameCategory> findAll(Pageable pageable){
    return gameCategoryRepository.findAllByStatusIn(GameCategory.getListStatusShow(), pageable);
  }
}
