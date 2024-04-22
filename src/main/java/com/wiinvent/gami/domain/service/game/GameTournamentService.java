package com.wiinvent.gami.domain.service.game;

import com.wiinvent.gami.domain.dto.GameTournamentCreateDto;
import com.wiinvent.gami.domain.dto.GameTournamentUpdateDto;
import com.wiinvent.gami.domain.entities.game.GameTournament;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.GameTournamentResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constants;
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
public class GameTournamentService extends BaseService {
  public GameTournamentResponse getGameTournamentDetail(String id){
    GameTournament gameTournament = gameTournamentStorage.findById(id);
    if (Objects.isNull(gameTournament)){
      throw new ResourceNotFoundException(Constants.TOURNAMENT_NOT_FOUND);
    }
    return modelMapper.toGameTournamentResponse(gameTournament);
  }

  public Page<GameTournamentResponse> findAll(Integer gameID, Status status, Pageable pageable){
    Page<GameTournament> gameTournaments = gameTournamentStorage.findAll(gameID, status, pageable);
    return modelMapper.toPageGameTournamentResponse(gameTournaments);
  }

  public void createGameTournament(GameTournamentCreateDto dto){
    GameTournament gameTournament = modelMapper.toGameTournament(dto);
    gameTournamentStorage.save(gameTournament);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void updateGameTournament(String id, GameTournamentUpdateDto dto){
    GameTournament gameTournament = gameTournamentStorage.findById(id);
    if (Objects.isNull(gameTournament)){
      throw new ResourceNotFoundException(Constants.TOURNAMENT_NOT_FOUND);
    }
    modelMapper.mapGameTournamentUpdateDtoToGameTournament(dto, gameTournament);
    gameTournamentStorage.save(gameTournament);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void deleteGameTournament(String id) {
    GameTournament gameTournament = gameTournamentStorage.findByIdAndStatusNot(id, Status.DELETE);
    if (Objects.isNull(gameTournament)) {
      throw new BadRequestException(Constants.GAME_PACKAGE_NOT_FOUND);
    }
    gameTournament.setStatus(Status.DELETE);
    gameTournamentStorage.save(gameTournament);
  }
}
