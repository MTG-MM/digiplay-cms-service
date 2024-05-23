package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.PackageCreateDto;
import com.wiinvent.gami.domain.dto.PackageUpdateDto;
import com.wiinvent.gami.domain.dto.gvc.GvcPackageCreateDto;
import com.wiinvent.gami.domain.dto.gvc.GvcPackageUpdateDto;
import com.wiinvent.gami.domain.entities.type.CharacterCategoryType;
import com.wiinvent.gami.domain.entities.type.ProductType;
import com.wiinvent.gami.domain.response.CharacterResponse;
import com.wiinvent.gami.domain.response.GvcPackageResponse;
import com.wiinvent.gami.domain.response.PackageResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.GvcPackageService;
import com.wiinvent.gami.domain.service.PackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/portal/package")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
@Tag(name = "Product", description = "Api cho gói portal(ví dụ: SUB_01, SUB_o2,...), các gói này là gòi con của package type(SUB, PREMIUM, ...)")
public class PackageController {
  @Autowired
  private PackageService packageService;

  @Autowired private GvcPackageService gvcPackageService;

  //=========================================== NORMAL PACKAGE ============================================
  @GetMapping("")
  @Operation(summary = "Lấy danh sách gói")
  public ResponseEntity<PageResponse<PackageResponse>> findAll(
      @RequestParam(required = false) Integer id,
      @RequestParam(required = false) Integer packageTypeId,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable
  ){
    return ResponseEntity.ok(
        PageResponse.createFrom(packageService.findAll(id, packageTypeId, pageable))
    );
  }

  @GetMapping("{id}")
  @Operation(summary = "Lấy thông tin chi tiết gói")
  public ResponseEntity<PackageResponse> getPackageDetail(@PathVariable int id){
    return ResponseEntity.ok(packageService.getPackageDetail(id));
  }

  @PostMapping("")
  @Operation(summary = "Tạo gói")
  public ResponseEntity<Boolean> createPackage(@RequestBody @Valid PackageCreateDto packageCreateDto){
    return ResponseEntity.ok(
        packageService.createPackage(packageCreateDto)
    );
  }

  @PutMapping("{id}")
  @Operation(summary = "Cập nhật thông tin gói")
  public ResponseEntity<Boolean> updatePackage(@PathVariable int id, @RequestBody @Valid PackageUpdateDto dto){
    return ResponseEntity.ok(
        packageService.updatePackage(id, dto)
    );
  }


  @DeleteMapping("{id}")
  @Operation(summary = "Xóa gói")
  public ResponseEntity<Boolean> deletePackage(@PathVariable int id){
    return ResponseEntity.ok(
        packageService.deletePackage(id)
    );
  }

  @GetMapping("all")
  public ResponseEntity<List<PackageResponse>> getAllPackages(
      @RequestParam(required = false) Integer typeId
  ) {
    return ResponseEntity.ok(packageService.getPackagesActive(typeId));
  }

  //==============================================  GVC PACKAGE ==============================================
  @GetMapping("/gvc")
  @Operation(summary = "Lấy danh sách gói")
  public ResponseEntity<PageResponse<GvcPackageResponse>> findAll(Pageable pageable){
    return ResponseEntity.ok(
        PageResponse.createFrom(gvcPackageService.findAll(pageable))
    );
  }

  @GetMapping("/gvc/{id}")
  @Operation(summary = "Lấy thông tin chi tiết gói")
  public ResponseEntity<GvcPackageResponse> getDetailGvcPackage(@PathVariable Integer id){
    return ResponseEntity.ok(
      gvcPackageService.getGvcPackageDetail(id)
    );
  }

  @PostMapping("/gvc")
  @Operation(summary = "Tạo gói")
  public ResponseEntity<Boolean> createGvcPackage(@RequestBody @Valid GvcPackageCreateDto dto){
    return ResponseEntity.ok(
      gvcPackageService.createGvcPackage(dto)
    );
  }

  @PutMapping("/gvc/{id}")
  @Operation(summary = "Cập nhật thông tin gói")
  public ResponseEntity<Boolean> updateGvcPackage(
      @PathVariable Integer id,
      @RequestBody @Valid GvcPackageUpdateDto dto){
    return ResponseEntity.ok(
        gvcPackageService.updateGvcPackage(id, dto)
    );
  }

  @DeleteMapping("/gvc/{id}")
  @Operation(summary = "Xóa gói")
  public ResponseEntity<Boolean> deleteGvcPackage(@PathVariable Integer id){
    return ResponseEntity.ok(
        gvcPackageService.deleteGvcPackage(id)
    );
  }
}
