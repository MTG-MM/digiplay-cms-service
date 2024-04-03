package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.GamePackageCreateDto;
import com.wiinvent.gami.domain.dto.GamePackageUpdateDto;
import com.wiinvent.gami.domain.dto.PackageCreateDto;
import com.wiinvent.gami.domain.dto.PackageUpdateDto;
import com.wiinvent.gami.domain.response.GamePackageResponse;
import com.wiinvent.gami.domain.response.PackageResponse;
import com.wiinvent.gami.domain.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("api/vt/cms/package")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")public class PackageController {
  @Autowired
  private PackageService packageService;

  @GetMapping("{id}")
  public ResponseEntity<PackageResponse> getPackageDetail(@PathVariable int id){
    return ResponseEntity.ok(packageService.getPackageDetail(id));
  }
  @PostMapping("")
  public ResponseEntity<Boolean> createGamePackage(@RequestBody PackageCreateDto packageCreateDto){
    packageService.createPackage(packageCreateDto);
    return ResponseEntity.ok(true);
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateGamePackage(@PathVariable int id, @RequestBody PackageUpdateDto dto){
    packageService.updatePackage(id, dto);
    return ResponseEntity.ok(true);
  }
}
