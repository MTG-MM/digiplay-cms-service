package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.*;
import com.wiinvent.gami.domain.entities.type.RequestGamiType;
import com.wiinvent.gami.domain.factory.GamiRequestInternalFactory;
import com.wiinvent.gami.domain.response.InternalRequestResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 86400)
@RestController
@RequestMapping("v1/portal/user/change")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class GamiRequestController {
  @Autowired
  private GamiRequestInternalFactory gamiRequestInternalFactory;

  @PutMapping("/sub")
  public ResponseEntity<InternalRequestResponse> processPackage(
      @RequestBody @Valid InternalSubRequestDto dto) {
    return ResponseEntity.ok(gamiRequestInternalFactory.addSub(dto));
  }

  @PutMapping("/process/reward")
  public ResponseEntity<InternalRequestResponse> processReward(
      @RequestParam UUID id) {
    return ResponseEntity.ok(gamiRequestInternalFactory.processReward(id));
  }

  @PutMapping("/task")
  public ResponseEntity<InternalRequestResponse> processTask(
      @RequestBody @Valid InternalTaskRequestDto dto) {
    return ResponseEntity.ok(gamiRequestInternalFactory.addTask(dto));
  }

  @PutMapping("/achievement")
  public ResponseEntity<InternalRequestResponse> processAchievement(
      @RequestBody @Valid InternalRequestDto dto) {
    return ResponseEntity.ok(gamiRequestInternalFactory.addAchievement(dto));
  }

  @PutMapping("/quest")
  public ResponseEntity<InternalRequestResponse> processQuest(
      @RequestBody @Valid InternalQuestRequestDto dto) {
    return ResponseEntity.ok(gamiRequestInternalFactory.addQuest(dto));
  }

  @PutMapping("/coin")
  public ResponseEntity<InternalRequestResponse> processCoin(
      @RequestBody @Valid InternalRequestDto dto) {
    if (Objects.equals(dto.getRequestType(), RequestGamiType.ADD)) {
      return ResponseEntity.ok(gamiRequestInternalFactory.addCoin(dto));
    } else {
      return ResponseEntity.ok(gamiRequestInternalFactory.subCoin(dto));
    }
  }

  @PutMapping("/point")
  public ResponseEntity<InternalRequestResponse> processPoint(
      @RequestBody @Valid InternalRequestDto dto) {
    if (Objects.equals(dto.getRequestType(), RequestGamiType.ADD)) {
      return ResponseEntity.ok(gamiRequestInternalFactory.addPoint(dto));
    } else {
      return ResponseEntity.ok(gamiRequestInternalFactory.subPoint(dto));
    }
  }

  @PutMapping("/exp")
  public ResponseEntity<InternalRequestResponse> processExp(
      @RequestBody @Valid InternalRequestDto dto) {
    if (Objects.equals(dto.getRequestType(), RequestGamiType.ADD)) {
      return ResponseEntity.ok(gamiRequestInternalFactory.addExp(dto));
    } else {
      return ResponseEntity.ok(gamiRequestInternalFactory.subExp(dto));
    }
  }

  @PutMapping("/ticket")
  public ResponseEntity<InternalRequestResponse> processTicket(
      @RequestBody @Valid InternalRequestDto dto) {
    if (Objects.equals(dto.getRequestType(), RequestGamiType.ADD)) {
      return ResponseEntity.ok(gamiRequestInternalFactory.addTicket(dto));
    } else {
      return ResponseEntity.ok(gamiRequestInternalFactory.subTicket(dto));
    }
  }

  @PutMapping("/turn")
  public ResponseEntity<InternalRequestResponse> processTurn(
      @RequestBody @Valid InternalRequestDto dto) {
    if (Objects.equals(dto.getRequestType(), RequestGamiType.ADD)) {
      return ResponseEntity.ok(gamiRequestInternalFactory.addTurn(dto));
    } else {
      return ResponseEntity.ok(gamiRequestInternalFactory.subTurn(dto));
    }
  }

  @PutMapping("/lucky-point")
  public ResponseEntity<InternalRequestResponse> processLuckyPoint(
      @RequestBody @Valid InternalRequestDto dto) {
    if (Objects.equals(dto.getRequestType(), RequestGamiType.ADD)) {
      return ResponseEntity.ok(gamiRequestInternalFactory.addLuckyPoint(dto));
    } else {
      return ResponseEntity.ok(gamiRequestInternalFactory.subLuckyPoint(dto));
    }
  }

  @PutMapping("/gold-pig")
  public ResponseEntity<InternalRequestResponse> processGoldPigPoint(
      @RequestBody @Valid InternalRequestDto dto) {
    if (Objects.equals(dto.getRequestType(), RequestGamiType.ADD)) {
      return ResponseEntity.ok(gamiRequestInternalFactory.addGoldPigPoint(dto));
    } else {
      return ResponseEntity.ok(gamiRequestInternalFactory.subGoldPigPoint(dto));
    }
  }


  @PutMapping("/collection")
  public ResponseEntity<InternalRequestResponse> processCollection(
      @RequestBody @Valid InternalRequestDto dto) {
    if (Objects.equals(dto.getRequestType(), RequestGamiType.ADD)) {
      return ResponseEntity.ok(gamiRequestInternalFactory.addCollection(dto));
    } else {
      return ResponseEntity.ok(gamiRequestInternalFactory.subCollection(dto));
    }
  }

  @PutMapping("/character")
  public ResponseEntity<InternalRequestResponse> processCharacter(
      @RequestBody @Valid InternalRequestDto dto) {
    if (Objects.equals(dto.getRequestType(), RequestGamiType.ADD)) {
      return ResponseEntity.ok(gamiRequestInternalFactory.addCharacter(dto));
    } else {
      return ResponseEntity.ok(gamiRequestInternalFactory.subCharacter(dto));
    }
  }

  @PutMapping("/password")
  public ResponseEntity<InternalRequestResponse> changePassword(
      @RequestBody @Valid InternalResetPassDto dto) {
    return ResponseEntity.ok(gamiRequestInternalFactory.resetPassword(dto));
  }
}
