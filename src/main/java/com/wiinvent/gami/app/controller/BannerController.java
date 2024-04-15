package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.BannerCreateDto;
import com.wiinvent.gami.domain.dto.BannerUpdateDto;
import com.wiinvent.gami.domain.entities.type.BannerType;
import com.wiinvent.gami.domain.response.BannerResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.BannerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/vt/cms/banner")
public class BannerController extends BaseController{
  @Autowired private BannerService bannerService;

  @GetMapping("")
  public ResponseEntity<PageResponse<BannerResponse>> findAll(
      @RequestParam BannerType type,
      Pageable pageable) {
    return ResponseEntity.ok(
        PageResponse.createFrom(bannerService.findAll(type, pageable))
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<BannerResponse> getBannerDetail(@PathVariable Integer id) {
    return ResponseEntity.ok(
        bannerService.getBannerDetail(id)
    );
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createBanner(@RequestBody @Valid BannerCreateDto dto){
    return ResponseEntity.ok(bannerService.createBanner(dto));
  }

  @PutMapping("")
  public ResponseEntity<Boolean> updateBanner(@RequestBody @Valid BannerUpdateDto dto){
    return ResponseEntity.ok(
        bannerService.updateBanner(dto)
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> deleteBanner(@PathVariable Integer id) {
    return ResponseEntity.ok(bannerService.deleteBanner(id));
  }
}