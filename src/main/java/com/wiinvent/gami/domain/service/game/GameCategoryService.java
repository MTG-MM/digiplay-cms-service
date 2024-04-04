package com.wiinvent.gami.domain.service.game;

import com.wiinvent.gami.domain.dto.GameCategoryCreateDto;
import com.wiinvent.gami.domain.dto.GameCategoryUpdateDto;
import com.wiinvent.gami.domain.entities.game.GameCategory;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.GameCategoryResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constant;
import com.wiinvent.gami.domain.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Log4j2
public class GameCategoryService extends BaseService {

  public Page<GameCategoryResponse> findAll(Pageable pageable){
    Page<GameCategory> categories = gameCategoryStorage.findAll(pageable);
    return modelMapper.toPageGameCategoryResponse(categories);
  }

  public GameCategoryResponse getGameCategoryDetail(Integer id){
    GameCategory gameCategory = gameCategoryStorage.findById(id);

    if(Objects.isNull(gameCategory)) throw new ResourceNotFoundException(Constant.GAME_CATEGORY_NOT_FOUND);

    return modelMapper.toGameCategoryResponse(gameCategory);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public boolean createGamePackage(GameCategoryCreateDto dto){
    if(Objects.isNull(dto.getStatus())) dto.setStatus(Status.ACTIVE);
    if(Objects.isNull(dto.getIsRequireSub())) dto.setIsRequireSub(false);

    GameCategory gameCategory = modelMapper.toGameCategory(dto);

    gameCategoryStorage.save(gameCategory);
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public boolean updateGameCategory(GameCategoryUpdateDto dto){
    GameCategory gameCategory = gameCategoryStorage.findById(dto.getId());

    if(Objects.isNull(gameCategory)) throw new ResourceNotFoundException(Constant.GAME_CATEGORY_NOT_FOUND);

    gameCategory.from(dto);
    gameCategory.setUpdatedAt(DateUtils.getNowMillisAtUtc());
    gameCategoryStorage.save(gameCategory);
    return true;
  }


}