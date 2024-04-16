package com.wiinvent.gami.domain.service.game;

import com.wiinvent.gami.domain.dto.GameCategoryCreateDto;
import com.wiinvent.gami.domain.dto.GameCategoryUpdateDto;
import com.wiinvent.gami.domain.dto.GameCategoryUpdateStatusDto;
import com.wiinvent.gami.domain.entities.game.GameCategory;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.GameCategoryResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constants;
import com.wiinvent.gami.domain.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class GameCategoryService extends BaseService {
  @Autowired @Lazy GameCategoryService self;

  public Page<GameCategoryResponse> findAll(Pageable pageable){
    Page<GameCategory> categories = gameCategoryStorage.findAll(pageable);
    return modelMapper.toPageGameCategoryResponse(categories);
  }

  public List<GameCategoryResponse> findAllGameCategoryActive(){
    List<GameCategory> categories = gameCategoryStorage.findAllGameCategoryActive();
    return modelMapper.toListGameCategoryResponse(categories);
  }

  public GameCategoryResponse getGameCategoryDetail(Integer id){
    GameCategory gameCategory = gameCategoryStorage.findById(id);

    if(Objects.isNull(gameCategory)) throw new ResourceNotFoundException(Constants.GAME_CATEGORY_NOT_FOUND);

    return modelMapper.toGameCategoryResponse(gameCategory);
  }

  public boolean createGameCategory(GameCategoryCreateDto dto){
    //validation
    if(Objects.isNull(dto.getStatus())) dto.setStatus(Status.ACTIVE);
    if(Objects.isNull(dto.getIsRequireSub())) dto.setIsRequireSub(false);
    //map
    GameCategory gameCategory = modelMapper.toGameCategory(dto);
    //save
    try{
      self.saveGameCategory(gameCategory);
    }catch (Exception e){
      log.debug("==================> createGameCategory:DB:Exception:{}", e.getMessage());
      return false;
    }
    //response
    return true;
  }

  public boolean updateGameCategory(GameCategoryUpdateDto dto){
    //validation
    GameCategory gameCategory = gameCategoryStorage.findById(dto.getId());
    if(Objects.isNull(gameCategory)) throw new ResourceNotFoundException(Constants.GAME_CATEGORY_NOT_FOUND);
    //map
    gameCategory.from(dto);
    gameCategory.setUpdatedAt(DateUtils.getNowMillisAtUtc());
    //save
    try{
      self.saveGameCategory(gameCategory);
    }catch (Exception e){
      log.debug("==================> updateGameCategory:DB:Exception:{}", e.getMessage());
      return false;
    }
    //response
    return true;
  }

  public boolean deleteGameCategory(Integer id){
    //validation
    GameCategory gameCategory = gameCategoryStorage.findById(id);
    if(Objects.isNull(gameCategory)) throw new ResourceNotFoundException(Constants.GAME_CATEGORY_NOT_FOUND);
    //map
    gameCategory.setStatus(Status.DELETE);
    gameCategory.setUpdatedAt(DateUtils.getNowMillisAtUtc());
    //save
    try{
      self.saveGameCategory(gameCategory);
    }catch (Exception e){
      log.debug("==================> deleteGameCategory:DB:Exception:{}", e.getMessage());
      return false;
    }
    //response
    return true;
  }

  public boolean updateStatusGameCategory(GameCategoryUpdateStatusDto dto){
    //validation
    GameCategory gameCategory = gameCategoryStorage.findById(dto.getId());
    if(Objects.isNull(gameCategory)) throw new ResourceNotFoundException(Constants.GAME_CATEGORY_NOT_FOUND);
    if(dto.getStatus().equals(gameCategory.getStatus())) throw new ResourceNotFoundException(Constants.GAME_CATEGORY_STATUS_NOT_CHANGE);
    //map
    gameCategory.setStatus(dto.getStatus());
    gameCategory.setUpdatedAt(DateUtils.getNowMillisAtUtc());
    //save
    try{
      self.updateStatusGame(gameCategory);
    }catch (Exception e){
      log.debug("==================> updateStatusGameCategory:DB:Exception:{}", e.getMessage());
      return false;
    }
    //response
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void saveGameCategory(GameCategory gameCategory){
    gameCategoryStorage.save(gameCategory);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void updateStatusGame(GameCategory gameCategory){
    //cap nhat lai game category
    gameCategoryStorage.save(gameCategory);
    //cap nhat lai trang thai cua game co category nay
//    List<Game> games = null;
//    if(gameCategory.getStatus().equals(Status.INACTIVE)) games = gameStorage.findAllByCategoryId(gameCategory.getId(), Game.getListStatusReady());
//    else if(gameCategory.getStatus().equals(Status.ACTIVE)) games = gameStorage.findAllByCategoryId(gameCategory.getId(), List.of(GameStatus.INACTIVE));
//    else games = new ArrayList<>();
//    games.forEach(g->{
//      g.setStatus(gameCategory.getStatus().equals(Status.INACTIVE)?GameStatus.INACTIVE:GameStatus.ACTIVE);
//      g.setUpdatedAt(DateUtils.getNowMillisAtUtc());
//    });
//    gameStorage.saveAll(games);
  }

}