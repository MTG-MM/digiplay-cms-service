package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.InternalAchievementRequestDto;
import com.wiinvent.gami.domain.factory.GamiRequestInternalFactory;
import com.wiinvent.gami.domain.dto.InternalRequestDto;
import com.wiinvent.gami.domain.dto.InternalResetPassDto;
import com.wiinvent.gami.domain.dto.InternalSubRequestDto;
import com.wiinvent.gami.domain.response.InternalRequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 86400)
@RestController
@RequestMapping("v1/portal/user/change")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class GamiRequestController {
  @Autowired
  private GamiRequestInternalFactory gamiRequestInternalFactory;

  @PutMapping("/sub")
  public ResponseEntity<InternalRequestResponse> processPackage(
      @RequestBody InternalSubRequestDto dto) {
    return ResponseEntity.ok(gamiRequestInternalFactory.addSub(dto));
  }

  @PutMapping("/achievement")
  public ResponseEntity<InternalRequestResponse> processAchievement(
      @RequestBody InternalAchievementRequestDto dto) {
    return ResponseEntity.ok(gamiRequestInternalFactory.addAchievement(dto));
  }

  @PutMapping("/coin")
  public ResponseEntity<InternalRequestResponse> processCoin(
      @RequestBody InternalRequestDto dto) {
    if (Objects.equals(dto.getRequestType(), InternalRequestDto.RequestGamiType.ADD)) {
      return ResponseEntity.ok(gamiRequestInternalFactory.addCoin(dto));
    } else {
      return ResponseEntity.ok(gamiRequestInternalFactory.subCoin(dto));
    }
  }

  @PutMapping("/point")
  public ResponseEntity<InternalRequestResponse> processPoint(
      @RequestBody InternalRequestDto dto) {
    if (Objects.equals(dto.getRequestType(), InternalRequestDto.RequestGamiType.ADD)) {
      return ResponseEntity.ok(gamiRequestInternalFactory.addPoint(dto));
    } else {
      return ResponseEntity.ok(gamiRequestInternalFactory.subPoint(dto));
    }
  }

  @PutMapping("/exp")
  public ResponseEntity<InternalRequestResponse> processExp(
      @RequestBody InternalRequestDto dto) {
    if (Objects.equals(dto.getRequestType(), InternalRequestDto.RequestGamiType.ADD)) {
      return ResponseEntity.ok(gamiRequestInternalFactory.addExp(dto));
    } else {
      return ResponseEntity.ok(gamiRequestInternalFactory.subExp(dto));
    }
  }

  @PutMapping("/ticket")
  public ResponseEntity<InternalRequestResponse> processTicket(
      @RequestBody InternalRequestDto dto) {
    if (Objects.equals(dto.getRequestType(), InternalRequestDto.RequestGamiType.ADD)) {
      return ResponseEntity.ok(gamiRequestInternalFactory.addTicket(dto));
    } else {
      return ResponseEntity.ok(gamiRequestInternalFactory.subTicket(dto));
    }
  }

  @PutMapping("/turn")
  public ResponseEntity<InternalRequestResponse> processTurn(
      @RequestBody InternalRequestDto dto) {
    if (Objects.equals(dto.getRequestType(), InternalRequestDto.RequestGamiType.ADD)) {
      return ResponseEntity.ok(gamiRequestInternalFactory.addTurn(dto));
    } else {
      return ResponseEntity.ok(gamiRequestInternalFactory.subTurn(dto));
    }
  }

  @PutMapping("/lucky-point")
  public ResponseEntity<InternalRequestResponse> processLuckyPoint(
      @RequestBody InternalRequestDto dto) {
    if (Objects.equals(dto.getRequestType(), InternalRequestDto.RequestGamiType.ADD)) {
      return ResponseEntity.ok(gamiRequestInternalFactory.addLuckyPoint(dto));
    } else {
      return ResponseEntity.ok(gamiRequestInternalFactory.subLuckyPoint(dto));
    }
  }


  @PutMapping("/collection")
  public ResponseEntity<InternalRequestResponse> processCollection(
      @RequestBody InternalRequestDto dto) {
    if (Objects.equals(dto.getRequestType(), InternalRequestDto.RequestGamiType.ADD)) {
      return ResponseEntity.ok(gamiRequestInternalFactory.addCollection(dto));
    } else {
      return ResponseEntity.ok(gamiRequestInternalFactory.subCollection(dto));
    }
  }

  @PutMapping("/password")
  public ResponseEntity<InternalRequestResponse> changePassword(
      @RequestBody InternalResetPassDto dto) {
    return ResponseEntity.ok(gamiRequestInternalFactory.resetPassword(dto));
  }
}
