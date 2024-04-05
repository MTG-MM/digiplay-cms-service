package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.GamePackageCreateDto;
import com.wiinvent.gami.domain.dto.GamePackageUpdateDto;
import com.wiinvent.gami.domain.dto.PackageCreateDto;
import com.wiinvent.gami.domain.dto.PackageUpdateDto;
import com.wiinvent.gami.domain.entities.type.PackageType;
import com.wiinvent.gami.domain.response.GamePackageResponse;
import com.wiinvent.gami.domain.response.PackageResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("api/vt/cms/package")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")public class PackageController {
  @Autowired
  private PackageService packageService;

  @GetMapping("")
  public ResponseEntity<PageResponse<PackageResponse>> findAll(
      @RequestParam(required = false) Integer id,
      @RequestParam(required = false) PackageType type,
      Pageable pageable
  ){
    return ResponseEntity.ok(
        PageResponse.createFrom(packageService.findAll(id, type, pageable))
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<PackageResponse> getPackageDetail(@PathVariable int id){
    return ResponseEntity.ok(packageService.getPackageDetail(id));
  }
  @PostMapping("")
  public ResponseEntity<Boolean> createPackage(@RequestBody PackageCreateDto packageCreateDto){
    return ResponseEntity.ok(
        packageService.createPackage(packageCreateDto)
    );
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updatePackage(@PathVariable int id, @RequestBody PackageUpdateDto dto){
    return ResponseEntity.ok(
        packageService.updatePackage(id, dto)
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> deletePackage(@PathVariable int id){
    return ResponseEntity.ok(
        packageService.deletePackage(id)
    );
  }
}
