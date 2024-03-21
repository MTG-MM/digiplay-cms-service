package com.wiinvent.gami.domain.service.game;

import com.wiinvent.gami.domain.dto.GameCreateDto;
import com.wiinvent.gami.domain.dto.GameUpdateDto;
import com.wiinvent.gami.domain.response.GameResponse;
import com.wiinvent.gami.domain.entities.game.Game;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GameService extends BaseService {
  public GameResponse getGameDetail(Integer id) {
    Game game = gameStorage.findById(id);
    if (game == null) {
      throw new ResourceNotFoundException(Constant.GAME_NOT_FOUND);
    }
    return modelMapper.toGameResponse(game);
  }

  public Page<GameResponse> getAll(Integer id, String name, Pageable pageable) {
    Page<Game> games = gameStorage.findAll(id, name, pageable);
    Page<GameResponse> responses = modelMapper.toPageGameResponse(games);
    return responses;
  }

  public void createGames(GameCreateDto createDto) {
    Game game = modelMapper.toGame(createDto);
    gameStorage.save(game);
  }

  public void updateGame(Integer id, GameUpdateDto updateDto) {
    Game game = gameStorage.findById(id);
    if (game == null) {
      throw new ResourceNotFoundException(Constant.GAME_NOT_FOUND);
    }
    modelMapper.mapGameUpdateDtoToGame(updateDto, game);
    gameStorage.save(game);
  }
}