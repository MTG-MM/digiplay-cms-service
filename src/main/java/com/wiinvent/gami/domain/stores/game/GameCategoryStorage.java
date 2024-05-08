package com.wiinvent.gami.domain.stores.game;

import com.wiinvent.gami.domain.entities.game.GameCategory;
import com.wiinvent.gami.domain.entities.type.Status;
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
    remoteCache.del(genCacheKeys());
  }

  public GameCategory findById(Integer id){
    return gameCategoryRepository.findGameCategoryByIdAndStatusIn(id, GameCategory.getListStatusShow());
  }

  public Page<GameCategory> findAll(Pageable pageable){
    return gameCategoryRepository.findAllByStatusIn(GameCategory.getListStatusShow(), pageable);
  }

  public List<GameCategory> findAllGameCategoryActive(){
    return gameCategoryRepository.findGameCategoryByStatusIn(List.of(Status.ACTIVE));
  }

  public List<GameCategory> findGameCategoriesByIdIn(List<Integer> ids){
    return gameCategoryRepository.findGameCategoriesByIdIn(ids);
  }

  public List<String> genCacheKeys(){
    List<String> cacheKeys = new ArrayList<>();
    cacheKeys.add(cacheKey.genAllGameCategories());
    return cacheKeys;
  }
}
