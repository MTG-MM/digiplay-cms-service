package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.PackageTypeCreateDto;
import com.wiinvent.gami.domain.dto.PackageTypeUpdateDto;
import com.wiinvent.gami.domain.response.PackageTypeResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.PackageTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vt/cms/package-type")
@Tag(name = "loại gói", description = "ví dụ: PREMIUM, SUB, CHARGE")
public class PackageTypeController extends BaseController{
  @Autowired private PackageTypeService packageTypeService;

  @GetMapping("")
  public ResponseEntity<PageResponse<PackageTypeResponse>> findAll(Pageable pageable){
    return ResponseEntity.ok(
        PageResponse.createFrom(packageTypeService.findAll(pageable))
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
}