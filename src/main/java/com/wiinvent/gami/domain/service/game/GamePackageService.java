package com.wiinvent.gami.domain.service.game;

import com.wiinvent.gami.domain.dto.GamePackageCreateDto;
import com.wiinvent.gami.domain.dto.GamePackageUpdateDto;
import com.wiinvent.gami.domain.entities.game.Game;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.response.GamePackageResponse;
import com.wiinvent.gami.domain.entities.game.GamePackage;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constant;
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

import java.util.Objects;

@Service
@Log4j2
public class GamePackageService extends BaseService {
  @Autowired @Lazy GamePackageService self;

  public Page<GamePackageResponse> findAll(Integer gameId, Integer id, Status status, Pageable pageable){
    //validation
    Game game = gameStorage.findById(gameId);
    if(Objects.isNull(game)) throw new ResourceNotFoundException(Constant.GAME_NOT_FOUND);

    Page<GamePackage> gamePackages = gamePackageStorage.findAll(gameId, id, status,pageable);
    return modelMapper.toPageGamePackageResponse(gamePackages);
  }
  public GamePackageResponse getPackageDetail(int id) {
    //validation
    GamePackage gamePackage = gamePackageStorage.findById(id);
    if(gamePackage == null) throw new ResourceNotFoundException(Constant.GAME_PACKAGE_NOT_FOUND);
    Game game = gameStorage.findById(gamePackage.getGameId());
    if(Objects.isNull(game)) throw new ResourceNotFoundException(Constant.GAME_NOT_FOUND);

    return modelMapper.toGamePackageResponse(gamePackage);
  }

  public boolean createGamePackage(GamePackageCreateDto dto) {
    //validation
    if(Objects.isNull(dto.getStatus())) dto.setStatus(Status.ACTIVE);

    Game game = gameStorage.findById(dto.getGameId());
    if(Objects.isNull(game)) throw new ResourceNotFoundException(Constant.GAME_NOT_FOUND);
    //map
    GamePackage gamePackage = modelMapper.toGamePackage(dto);
    //save
    try {
      self.save(gamePackage);
    }catch (Exception e){
      log.error("==============>createGamePackage:DB:Exception:{}", e.getMessage());
      return false;
    }
    //response
    return true;
  }

  public boolean updateGamePackage(GamePackageUpdateDto dto) {
    //validation
    GamePackage gamePackage = gamePackageStorage.findById(dto.getId());
    if (gamePackage == null) throw new BadRequestException(Constant.GAME_PACKAGE_NOT_FOUND);
    Game game = gameStorage.findById(gamePackage.getGameId());
    if(Objects.isNull(game)) throw new ResourceNotFoundException(Constant.GAME_NOT_FOUND);
    //map
    modelMapper.mapGamePackageUpdateDtoToGamePackage(dto, gamePackage);
    gamePackage.setUpdatedAt(DateUtils.getNowMillisAtUtc());
    //save
    try {
      self.save(gamePackage);
    }catch (Exception e){
      log.error("==============>updateGamePackage:DB:Exception:{}", e.getMessage());
      return false;
    }
    //response
    return true;
  }

  public boolean deleteGamePackage(Integer id) {
    //validation
    GamePackage gamePackage = gamePackageStorage.findById(id);
    if (Objects.isNull(gamePackage)) throw new BadRequestException(Constant.GAME_PACKAGE_NOT_FOUND);
    Game game = gameStorage.findById(gamePackage.getGameId());
    if(Objects.isNull(game)) throw new ResourceNotFoundException(Constant.GAME_NOT_FOUND);
    //map
    gamePackage.setStatus(Status.DELETE);
    gamePackage.setUpdatedAt(DateUtils.getNowMillisAtUtc());
    //save
    try {
      self.save(gamePackage);
    }catch (Exception e){
      log.error("==============>deleteGamePackage:DB:Exception:{}", e.getMessage());
      return false;
    }
    //response
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void save(GamePackage gamePackage){
    gamePackageStorage.save(gamePackage);
  }
}