package com.wiinvent.gami.domain.stores.game;

import com.wiinvent.gami.domain.entities.game.GameCategory;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameCategoryStorage extends BaseStorage {
  public void save(GameCategory gameCategory){
    gameCategoryRepository.save(gameCategory);
    remoteCache.del(genCacheKeys(gameCategory));
  }

  public GameCategory findById(Integer id){
    return gameCategoryRepository.findGameCategoryByIdAndStatusIn(id, GameCategory.getListStatusShow());
  }

  public Page<GameCategory> findAll(Pageable pageable){
    return gameCategoryRepository.findAllByStatusIn(GameCategory.getListStatusShow(), pageable);
  }

  public List<String> genCacheKeys(GameCategory gameCategory){
    List<String> cacheKeys = new ArrayList<>();
    cacheKeys.add(cacheKey.genAllGameCategories());
    cacheKeys.add(cacheKey.getGameByCategoryId(gameCategory.getId(),0));
    cacheKeys.add(cacheKey.getGameByCategoryId(gameCategory.getId(),1));
    cacheKeys.add(cacheKey.getGameByCategoryId(gameCategory.getId(),2));
    cacheKeys.add(cacheKey.getGameByCategoryId(gameCategory.getId(),3));
    cacheKeys.add(cacheKey.getGameByCategoryId(gameCategory.getId(),4));
    return cacheKeys;
  }
}
