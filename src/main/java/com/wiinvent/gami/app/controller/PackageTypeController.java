package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.PackageTypeCreateDto;
import com.wiinvent.gami.domain.dto.PackageTypeUpdateDto;
import com.wiinvent.gami.domain.entities.type.ProductPackageType;
import com.wiinvent.gami.domain.response.PackageTypeResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.PackageTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/portal/package-type")
@Tag(name = "Product included(Type)", description = "ví dụ: PREMIUM, SUB, CHARGE, trong các gói này sẽ gồm nhiều package con.")
public class PackageTypeController extends BaseController{
  @Autowired private PackageTypeService packageTypeService;

  @GetMapping("")
  public ResponseEntity<PageResponse<PackageTypeResponse>> findAll(
      @RequestParam(required = false) ProductPackageType type,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable){
    return ResponseEntity.ok(
        PageResponse.createFrom(packageTypeService.findAll(type, pageable))
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<PackageTypeResponse> getPackageTypeDetail(@PathVariable Integer id){
    return ResponseEntity.ok(
        packageTypeService.findPackageTypeDetail(id)
    );
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createPackageType(@RequestBody @Valid PackageTypeCreateDto dto){
    return ResponseEntity.ok(
        packageTypeService.createPackageType(dto)
    );
  }

  @PutMapping("")
  public ResponseEntity<Boolean> updatePackageType(@RequestBody @Valid PackageTypeUpdateDto dto){
    return ResponseEntity.ok(
        packageTypeService.updatePackageType(dto)
    );
  }

  @GetMapping("all")
  public ResponseEntity<List<PackageTypeResponse>> findAllActive(){
    return ResponseEntity.ok(
        packageTypeService.findAllPackageTypeActive()
    );
  }
}