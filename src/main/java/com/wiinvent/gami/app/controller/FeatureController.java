package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.FeatureCreateDto;
import com.wiinvent.gami.domain.dto.FeatureUpdateDto;
import com.wiinvent.gami.domain.entities.type.FeatureCode;
import com.wiinvent.gami.domain.response.FeatureResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.FeatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/vt/cms/feature")
@Tag(name = "Feature", description = "feature")
public class FeatureController extends BaseController {
  @Autowired private FeatureService featureService;

  @GetMapping("")
  @Operation(summary = "Lấy danh sách tính năng")
  public ResponseEntity<PageResponse<FeatureResponse>> findAll(Pageable pageable){
    return ResponseEntity.ok(
        PageResponse.createFrom(featureService.findAll(pageable))
    );
  }

  @GetMapping("{id}")
  @Operation(summary = "Lấy chi tiết thông tin tính năng")
  public ResponseEntity<FeatureResponse> getFeatureDetail(@PathVariable Integer id){
    return ResponseEntity.ok(
        featureService.getFeatureDetail(id)
    );
  }

  @PostMapping("")
  @Operation(summary = "Tạo tính năng")
  public ResponseEntity<Boolean> createFeature(@RequestBody @Valid FeatureCreateDto dto){
    return ResponseEntity.ok(
        featureService.createFeature(dto)
    );
  }

  @PutMapping("")
  @Operation(summary = "Cập nhật thông tin tính năng")
  public ResponseEntity<Boolean> updateFeature(@RequestBody @Valid FeatureUpdateDto dto){
    return ResponseEntity.ok(
        featureService.updateFeature(dto)
    );
  }

  @DeleteMapping("{id}")
  @Operation(summary = "Xóa tính năng")
  public ResponseEntity<Boolean> deleteFeature(@PathVariable Integer id){
    return ResponseEntity.ok(
        featureService.deleteFeature(id)
    );
  }

  @GetMapping("/code")
  @Operation(summary = "Lấy danh sách mã tính năng")
  public ResponseEntity<List<FeatureCode>> findAllFeatureCode(){
    return ResponseEntity.ok(
        featureService.findAllFeatureCode()
    );
  }
}